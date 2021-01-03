package com.geekalliance.taurus.rdb.utils;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.extension.toolkit.JdbcUtils;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.hollysys.platform.common.core.service.DataSourceService;
import com.hollysys.platform.common.core.toolkit.DataSourceContextHolder;
import com.hollysys.platform.common.core.toolkit.StringPool;
import com.hollysys.platform.common.rdb.config.DynamicDataSourceConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.ExceptionUtil;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.MyBatisExceptionTranslator;
import org.mybatis.spring.SqlSessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author maxuqiang
 */
@Slf4j
public class ExclusiveLockUtils {
    public synchronized static List<Connection> lockTableAllNodeMasterDb(String tableName, String fieldName) throws SQLException {
        List<Connection> lockConnections = new ArrayList<>();
        Map<Object, Object> dataSources = DynamicDataSourceConfig.getAllDataSource();
        if (Objects.nonNull(dataSources) && !dataSources.isEmpty()) {
            Set<Object> keys = dataSources.keySet().stream()
                    .filter(data -> data.toString().startsWith(DataSourceService.MASTER_DATASOURCE_TAG) && !data.toString().equals(DataSourceService.MASTER_DATASOURCE_TAG))
                    .collect(Collectors.toSet());
            lockByAllNode(keys, lockConnections, tableName, fieldName);
        }
        if (lockConnections.isEmpty()) {
            throw new RuntimeException("lock table all node master db error because lockConnections is empty");
        }
        return lockConnections;
    }

    /**
     * 锁表顺序为
     * AA（主节点A网卡） AB（主节点B网卡） BA（从节点A网卡） BB（从节点B网卡）
     *
     * @param keys
     * @param lockConnections
     * @param tableName
     * @param fieldName
     * @throws SQLException
     */
    private static void lockByAllNode(Set<Object> keys, List<Connection> lockConnections, String tableName, String fieldName) throws SQLException {
        if (!CollectionUtils.isEmpty(keys)) {
            String keyAA = StringPool.EMPTY, keyAB = StringPool.EMPTY, keyBA = StringPool.EMPTY, keyBB = StringPool.EMPTY;
            for (Object key : keys) {
                if (key.toString().endsWith(StringPool.A + StringPool.A)) {
                    keyAA = key.toString();
                }
                if (key.toString().endsWith(StringPool.A + StringPool.B)) {
                    keyAB = key.toString();
                }
                if (key.toString().endsWith(StringPool.B + StringPool.A)) {
                    keyBA = key.toString();
                }
                if (key.toString().endsWith(StringPool.B + StringPool.B)) {
                    keyBB = key.toString();
                }
            }
            try {
                // 锁主节点A网IP地址，锁表成功后直接锁从节点，锁定失败后尝试锁定主节点B网IP地址
                lockByDataSourceKey(keyAA, lockConnections, tableName, fieldName);
            } catch (Exception e) {
                try {
                    lockByDataSourceKey(keyAB, lockConnections, tableName, fieldName);
                } catch (Exception ex) {
                }
            }
            try {
                // 锁从节点A网IP地址，锁表成功后结束，锁定失败后尝试锁定从节点B网IP地址
                lockByDataSourceKey(keyBA, lockConnections, tableName, fieldName);
            } catch (Exception e) {
                try {
                    lockByDataSourceKey(keyBB, lockConnections, tableName, fieldName);
                } catch (Exception ex) {
                }
            }
        } else {
            lockByDataSourceKey(DataSourceService.MASTER_DATASOURCE_TAG, lockConnections, tableName, fieldName);
        }
    }

    private static void lockByDataSourceKey(String key, List<Connection> lockConnections, String tableName, String fieldName) throws SQLException {
        if (!StringUtils.isNotBlank(key)) {
            return;
        }
        try {
            Connection connection = DynamicDataSourceConfig.getConnection(key, 10000L);
            lockTable(connection, tableName, fieldName);
            log.info("tag {} lock table {} field {} success", key, tableName, fieldName);
            lockConnections.add(connection);
        } catch (Exception e) {
            log.error("tag {} lock table {} field {} fail {}", key, tableName, fieldName, e.getMessage());
            throw e;
        }
    }

    public static void lockTable(SqlSession sqlSession, String tableName, String fieldName) throws SQLException {
        if (Objects.isNull(sqlSession)) {
            throw new RuntimeException("lock table sqlSession is null");
        }
        lockTable(sqlSession.getConnection(), tableName, fieldName);
    }

    public static void lockTable(Connection connection, String tableName, String fieldName) throws SQLException {
        if (Objects.isNull(connection)) {
            throw new RuntimeException("lock table connection is null");
        }
        if (!StringUtils.isNotBlank(tableName) || !StringUtils.isNotBlank(fieldName)) {
            throw new RuntimeException("lock table tableName or fieldName is blank");
        }
        String lockSql = getLockSql(tableName, fieldName);
        if (StringUtils.isNotBlank(lockSql)) {
            PreparedStatement preparedStatement = null;
            try {
                connection.setAutoCommit(false);
                preparedStatement = connection.prepareStatement(lockSql);
                preparedStatement.execute();
                preparedStatement.close();
                log.info("successfully lock table {}", tableName);
            } catch (SQLException e) {
                connection.close();
                throw e;
            }
        }
    }

    public static void releaseLock(List<Connection> connections) throws SQLException {
        if (!CollectionUtils.isEmpty(connections)) {
            connections.forEach(connection -> {
                try {
                    releaseLock(connection);
                } catch (Exception e) {
                    log.error("release lock error {}", e.getMessage());
                }
            });
        }
    }

    public static void releaseLock(Connection connection) throws SQLException {
        if (Objects.isNull(connection)) {
            throw new RuntimeException("release lock table connection is null");
        }
        try {
            connection.commit();
            connection.close();
            log.info("successfully released lock");
        } finally {
            connection.close();
        }
    }

    public static void releaseLock(SqlSession sqlSession) throws SQLException {
        if (Objects.isNull(sqlSession)) {
            throw new RuntimeException("release lock table sqlSession is null");
        }
        try {
            sqlSession.commit();
            sqlSession.close();
            log.info("successfully released lock");
        } finally {
            sqlSession.close();
        }
    }

    private static String getLockSql(String tableName, String fieldName) {
        DbType dbType = JdbcUtils.getDbType(DynamicDataSourceConfig.getDbUrl(DataSourceContextHolder.getDataSourceTag()));
        String lockSql = StringPool.EMPTY;
        switch (dbType) {
            case SQL_SERVER:
            case SQL_SERVER2005:
                lockSql = "SELECT " + fieldName + " FROM " + tableName + " WITH (UPDLOCK)";
                break;
            default:
                lockSql = "SELECT " + fieldName + " FROM " + tableName + " FOR UPDATE";
                break;
        }
        return lockSql;
    }
}
