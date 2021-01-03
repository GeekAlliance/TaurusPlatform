package com.geekalliance.taurus.rdb.holder;

import com.geekalliance.taurus.rdb.service.DataSourceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author maxuqiang
 */
@Slf4j
public class DataSourceContextHolder {
    private volatile static String CURR_DATASOURCE_TAG = DataSourceService.MASTER_DATASOURCE_TAG;

    private static final ThreadLocal<String> LOCAL = new ThreadLocal<String>();

    public static ThreadLocal<String> getLocal() {
        return LOCAL;
    }

    public static String getDataSourceTag() {
        String currThreadDataSource = LOCAL.get();
        if (StringUtils.isNotBlank(currThreadDataSource)) {
            return currThreadDataSource;
        } else {
            return CURR_DATASOURCE_TAG;
        }
    }

    public static void setDataSourceTag(String datasourceTag) {
        LOCAL.set(datasourceTag);
        log.debug("set {} datasource tag to local", datasourceTag);
    }

    public static void clearDataSource() {
        LOCAL.remove();
    }
}
