package com.geekalliance.taurus.rdb.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.vendor.MSSQLValidConnectionChecker;
import com.alibaba.druid.pool.vendor.MySqlValidConnectionChecker;
import com.alibaba.druid.pool.vendor.OracleValidConnectionChecker;
import com.alibaba.druid.pool.vendor.PGValidConnectionChecker;
import com.alibaba.druid.stat.DruidStatManagerFacade;
import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.druid.util.JdbcUtils;
import com.alibaba.druid.wall.WallProvider;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author maxuqiang
 */
@Data
@Slf4j
@ConfigurationProperties("spring.datasource.druid")
public class DruidDataSourceWrapper {
    public final static int DEFAULT_MAX_WAIT = -1;
    public final static int DEFAULT_MAX_ACTIVE_SIZE = 8;
    public final static int DEFAULT_MIN_IDLE = 0;
    public final static int DEFAULT_INITIAL_SIZE = 0;
    protected volatile long maxWait = DEFAULT_MAX_WAIT;
    protected volatile int maxActive = DEFAULT_MAX_ACTIVE_SIZE;
    protected volatile int minIdle = DEFAULT_MIN_IDLE;
    protected volatile int initialSize = DEFAULT_INITIAL_SIZE;
    protected boolean useGlobalDataSourceStat = false;
    protected volatile Properties connectProperties = new Properties();
    protected String filters;
    protected String userName;
    protected String password;

    public void afterPropertiesSet(DataSource ds) {
        if (ds instanceof DruidDataSource) {
            DruidDataSource dataSource = (DruidDataSource) ds;
            dataSource.setMaxWait(this.getMaxWait());
            dataSource.setMaxActive(this.getMaxActive());
            dataSource.setMinIdle(this.getMinIdle());
            dataSource.setInitialSize(this.getInitialSize());
            dataSource.setConnectProperties(connectProperties);
            dataSource.setUseGlobalDataSourceStat(useGlobalDataSourceStat);
            dataSource.setValidationQuery(getValidationQuerySql(dataSource.getDriverClassName()));
            try {
                dataSource.setFilters(filters);
            } catch (SQLException e) {
                log.error("druid data source wrapper after properties set error", e.getMessage());
            }
        }
    }

    private String getValidationQuerySql(String realDriverClassName) {
        String validationQuerySql = "select 1";
        if (realDriverClassName.equals(JdbcConstants.ORACLE_DRIVER)
                || realDriverClassName.equals(JdbcConstants.ORACLE_DRIVER2)) {
            validationQuerySql = "select 1 from dual";
        }
        return validationQuerySql;
    }
}
