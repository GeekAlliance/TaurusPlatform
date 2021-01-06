package com.geekalliance.taurus.rdb.utils;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.toolkit.JdbcUtils;
import com.geekalliance.taurus.rdb.config.DynamicDataSourceConfig;
import com.geekalliance.taurus.rdb.holder.DataSourceContextHolder;
import com.geekalliance.taurus.toolkit.StringPool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * @author maxuqiang
 */
@Slf4j
public class ExclusiveLockUtils {


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
