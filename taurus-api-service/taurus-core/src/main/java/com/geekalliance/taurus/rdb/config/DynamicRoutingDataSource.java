package com.geekalliance.taurus.rdb.config;

import com.geekalliance.taurus.rdb.holder.DataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author maxuqiang
 */
@Slf4j
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        Object dataSourceTag = getDataSourceTag();
        log.debug("use datasource {}", dataSourceTag);
        return dataSourceTag;
    }

    public static Object getDataSourceTag() {
        String dataSourceTag = DataSourceContextHolder.getDataSourceTag();
        Map<Object, Object> dataSource = DynamicDataSourceConfig.getAllDataSource();
        if (Objects.nonNull(dataSource) && !dataSource.isEmpty()) {
            Set<Object> dataSourceKey = dataSource.keySet();
            String currDataSource = DataSourceContextHolder.getDataSourceTag() + DynamicDataSourceConfig.getRealDbServiceIp();
            for (Object key : dataSourceKey) {
                if (key.toString().startsWith(currDataSource)) {
                    return key;
                }
            }
        }
        return dataSourceTag;
    }
}
