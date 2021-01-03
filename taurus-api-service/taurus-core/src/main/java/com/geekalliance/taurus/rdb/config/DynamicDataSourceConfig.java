package com.geekalliance.taurus.rdb.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.geekalliance.taurus.rdb.enums.DataBaseTypeEnum;
import com.geekalliance.taurus.rdb.holder.DataSourceContextHolder;
import com.geekalliance.taurus.rdb.service.AbstractDataSourceService;
import com.geekalliance.taurus.rdb.service.DataSourceService;
import com.geekalliance.taurus.toolkit.StringPool;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.servicelocator.ServiceLocator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * @author maxuqiang
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({DynamicDataSourceProperties.class, DataSourceProperties.class, DruidDataSourceWrapper.class})
public class DynamicDataSourceConfig extends AbstractDataSourceService implements ApplicationListener<ApplicationReadyEvent> {
    private static Map<Object, Object> DATA_SOURCES = new HashMap<>(1);

    private static String REAL_DB_SERVICE_IP = StringPool.EMPTY;

    private static DynamicRoutingDataSource routingDataSource = new DynamicRoutingDataSource();

    public static String getRealDbServiceIp() {
        return REAL_DB_SERVICE_IP;
    }

    @Resource
    private DynamicDataSourceProperties dynamicDataSourceProperties;

    @Resource
    private DataSourceProperties dataSourceProperties;

    @Resource
    private DruidDataSourceWrapper druidDataSourceWrapper;

    @Autowired
    private Environment environment;

    @Bean
    @Primary
    public AbstractRoutingDataSource dynamicDataSource() {
        initDataSource();
        ServiceLocator.getInstance().addPackageToScan("com.hollysys.platform.common.rdb.service.liquibase");
        return routingDataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(routingDataSource);
    }

    @Override
    public Map<Object, Object> initDataSource() {
        synchronized (LOCK) {
            // 初始化默认数据源
            if (!StringUtils.isNotBlank(REAL_DB_SERVICE_IP)) {
                REAL_DB_SERVICE_IP = environment.getProperty("spring.datasource.addr");
            }
            initDataSourceByIps();
            if (!DATA_SOURCES.isEmpty()) {
                Set<Object> dataSourceKey = DATA_SOURCES.keySet();
                String defaultMasterDataSourceTag = MASTER_DATASOURCE_TAG + environment.getProperty("spring.datasource.addr");
                String defaultBackupDataSourceTag = BACKUP_DATASOURCE_TAG + environment.getProperty("spring.datasource.addr");
                for (Object key : dataSourceKey) {
                    if (key.toString().startsWith(defaultMasterDataSourceTag)) {
                        defaultMasterDataSourceTag = key.toString();
                    }
                    if (key.toString().startsWith(defaultBackupDataSourceTag)) {
                        defaultBackupDataSourceTag = key.toString();
                    }
                }
                DATA_SOURCES.put(MASTER_DATASOURCE_TAG, DATA_SOURCES.get(defaultMasterDataSourceTag));
                DATA_SOURCES.put(BACKUP_DATASOURCE_TAG, DATA_SOURCES.get(defaultBackupDataSourceTag));
            }

            if (Objects.isNull(DATA_SOURCES.get(MASTER_DATASOURCE_TAG))) {
                initDefaultDataSource(MASTER_DATASOURCE_TAG, environment.getProperty("spring.datasource.dbname"));
            }

            if (Objects.isNull(DATA_SOURCES.get(BACKUP_DATASOURCE_TAG))) {
                initDefaultDataSource(BACKUP_DATASOURCE_TAG, environment.getProperty("spring.datasource.backup.dbname"));
            }
            // 关闭不存在的数据源
            Set<Object> dataSourceKeys = getDataSourceKeys();
            for (Object k : dataSourceKeys) {
                if (!k.equals(MASTER_DATASOURCE_TAG) && !k.equals(BACKUP_DATASOURCE_TAG)) {
                    if (cleanNotExistDataSource(k)) {
                        log.info("data source {} not exist clean it", k);
                    }
                }
            }
            routingDataSource.setDefaultTargetDataSource(DATA_SOURCES.get(MASTER_DATASOURCE_TAG));
            routingDataSource.setTargetDataSources(DATA_SOURCES);
            log.info("real db service ip {}", REAL_DB_SERVICE_IP);
            log.info("config dataSource count {} {}", DATA_SOURCES.size(), DATA_SOURCES.keySet());
        }
        return DATA_SOURCES;
    }

    private void initDefaultDataSource(String tag, String dbname) {
        String dataSourceAddr = environment.getProperty("spring.datasource.addr");
        String dbType = environment.getProperty("spring.datasource.dbtype");
        if (Objects.isNull(dbType)) {
            dbType = DataBaseTypeEnum.SQLSERVER.name();
        } else {
            dbType = dbType.toUpperCase();
        }
        DataBaseTypeEnum dataBaseTypeEnum = DataBaseTypeEnum.valueOf(dbType);
        String url = dataBaseTypeEnum.getUrl().replace("${spring.datasource.addr}", dataSourceAddr);
        url = url.replace("${spring.datasource.port}", dataBaseTypeEnum.getPort());
        url = url.replace("${spring.datasource.dbname}", dbname);
        initDataSourceByUrl(tag, url);
    }

    private void initDataSourceByIps() {
        String dbType = environment.getProperty("spring.datasource.dbtype");
        if (Objects.isNull(dbType)) {
            dbType = DataBaseTypeEnum.SQLSERVER.name();
        } else {
            dbType = dbType.toUpperCase();
        }
        DataBaseTypeEnum dataBaseTypeEnum = DataBaseTypeEnum.valueOf(dbType);
        Map<String, Map<String, String>> ips = dynamicDataSourceProperties.getIps();
        if (Objects.nonNull(ips) && !ips.isEmpty()) {
            Set<String> dataBaseTags = ips.keySet();
            for (String tag : dataBaseTags) {
                initDataSourceByNode(tag, ips.get(tag), dataBaseTypeEnum);
            }
        }
    }

    private void initDataSourceByNode(String tag, Map<String, String> nodeUrls, DataBaseTypeEnum dataBaseTypeEnum) {
        if (Objects.nonNull(nodeUrls) && !nodeUrls.isEmpty()) {
            Set<String> dataBasNodes = nodeUrls.keySet();
            for (String node : dataBasNodes) {
                String url = dataBaseTypeEnum.getUrl().replace("${spring.datasource.addr}", nodeUrls.get(node));
                url = url.replace("${spring.datasource.port}", dataBaseTypeEnum.getPort());
                if (tag.equals(DataSourceService.MASTER_DATASOURCE_TAG)) {
                    url = url.replace("${spring.datasource.dbname}", environment.getProperty("spring.datasource.dbname"));
                    initDataSourceByUrl(tag + node, url);
                } else if (tag.equals(DataSourceService.BACKUP_DATASOURCE_TAG)) {
                    url = url.replace("${spring.datasource.dbname}", environment.getProperty("spring.datasource.backup.dbname"));
                    initDataSourceByUrl(tag + node, url);
                }
            }
        }
    }

    private void initDataSourceByUrl(String key, String url) {
        try {
            if (Objects.nonNull(DATA_SOURCES.get(key))) {
                return;
            }
            dataSourceProperties.setUrl(url);
            DataSource dataSource = dataSourceProperties.initializeDataSourceBuilder().build();
            druidDataSourceWrapper.afterPropertiesSet(dataSource);
            DATA_SOURCES.put(key, dataSource);
            if (dataSource instanceof DruidDataSource) {
                ((DruidDataSource) dataSource).init();
            }
            log.info("config datasource key {} url {}", key, url);
        } catch (Exception e) {
            log.error("config datasource key {} url {} error", key, url, e.getMessage());
        }
    }

    @Override
    public boolean cleanNotExistDataSource(Object dataSourceTag) {
        if (Objects.isNull(dataSourceTag) || !StringUtils.isNotBlank(dataSourceTag.toString())) {
            return false;
        }
        synchronized (LOCK) {
            Map<String, Map<String, String>> ips = dynamicDataSourceProperties.getIps();
            if (Objects.nonNull(ips) && !ips.isEmpty()) {
                boolean isExist = false;
                Set<String> dataBaseTags = ips.keySet();
                for (String tag : dataBaseTags) {
                    if (Objects.nonNull(ips.get(tag)) && !ips.get(tag).isEmpty()) {
                        Set<String> dataBasNodes = ips.get(tag).keySet();
                        for (String node : dataBasNodes) {
                            if (dataSourceTag.toString().equals(tag + node)) {
                                isExist = true;
                            }
                        }
                    }
                }
                if (!isExist) {
                    removeDataSource(dataSourceTag.toString());
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Set<Object> getDataSourceKeys() {
        Set<Object> dataSourceKeys = new TreeSet<>();
        dataSourceKeys.addAll(DATA_SOURCES.keySet());
        return dataSourceKeys;
    }

    @Override
    public String getCurrentDbUrl() {
        return getDbUrl(DataSourceContextHolder.getDataSourceTag());
    }

    private void removeDataSource(String dataSourceKey) {
        if (!Objects.isNull(DATA_SOURCES) && !DATA_SOURCES.isEmpty()) {
            Object dataSourceObj = DATA_SOURCES.get(dataSourceKey);
            if (dataSourceObj instanceof DruidDataSource) {
                DruidDataSource dataSource = (DruidDataSource) dataSourceObj;
                boolean success = false;
                while (!success) {
                    try {
                        dataSource.removeAbandoned();
                        success = true;
                        log.info("data source {} remove success", dataSourceKey);
                    } catch (Exception e) {
                        log.error("data source {} remove error {}", dataSourceKey, e.getMessage());
                        success = false;
                    }
                }
            } else if (dataSourceObj instanceof HikariDataSource) {
                HikariDataSource dataSource = (HikariDataSource) dataSourceObj;
                boolean success = false;
                while (!success) {
                    try {
                        dataSource.close();
                        success = true;
                        log.info("data source {} remove success", dataSourceKey);
                    } catch (Exception e) {
                        log.error("data source {} remove error {}", dataSourceKey, e.getMessage());
                        success = false;
                    }
                }
            }
            DATA_SOURCES.remove(dataSourceKey);
        }
    }

    private String getDataSourceUrl(Object dataSourceTag) {
        Object dataSourceObj = DATA_SOURCES.get(dataSourceTag);
        if (Objects.nonNull(dataSourceObj) && dataSourceObj instanceof DruidDataSource) {
            DruidDataSource dataSource = (DruidDataSource) dataSourceObj;
            return dataSource.getUrl();
        } else if (Objects.nonNull(dataSourceObj) && dataSourceObj instanceof HikariDataSource) {
            HikariDataSource dataSource = (HikariDataSource) dataSourceObj;
            return dataSource.getJdbcUrl();
        }
        return StringPool.EMPTY;
    }


    public static String getDbUrl(String dataSourceTag) {
        Object dataSourceObj = DATA_SOURCES.get(dataSourceTag);
        if (dataSourceObj instanceof DruidDataSource) {
            DruidDataSource dataSource = (DruidDataSource) dataSourceObj;
            return dataSource.getUrl();
        } else if (dataSourceObj instanceof HikariDataSource) {
            HikariDataSource dataSource = (HikariDataSource) dataSourceObj;
            return dataSource.getJdbcUrl();
        }
        return StringPool.EMPTY;
    }

    public static Map<Object, Object> getAllDataSource() {
        return Collections.unmodifiableMap(DATA_SOURCES);
    }

    @Deprecated
    public static Connection getConnection(Object dataSourceTag) {
        return getConnection(dataSourceTag, null);
    }

    public static Connection getConnection() {
        return getConnection(DynamicRoutingDataSource.getDataSourceTag(), null);
    }

    public static Connection getConnection(Object dataSourceTag, Long maxWaitMillis) {
        Object dataSourceObj = DATA_SOURCES.get(dataSourceTag);
        if (dataSourceObj instanceof DruidDataSource) {
            DruidDataSource dataSource = (DruidDataSource) dataSourceObj;
            try {
                if (Objects.nonNull(maxWaitMillis)) {
                    return dataSource.getConnection(maxWaitMillis);
                } else {
                    return dataSource.getConnection();
                }
            } catch (SQLException e) {
                return null;
            }
        } else if (dataSourceObj instanceof HikariDataSource) {
            HikariDataSource dataSource = (HikariDataSource) dataSourceObj;
            try {
                return dataSource.getConnection();
            } catch (SQLException e) {
                return null;
            }
        }
        return null;
    }


    private boolean hasDataSource(String ip) {
        Set<Object> keys = DATA_SOURCES.keySet();
        for (Object key : keys) {
            if (key.toString().contains(ip)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

    }
}
