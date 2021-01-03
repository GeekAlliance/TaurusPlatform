package com.geekalliance.taurus.rdb.utils;

import com.hollysys.platform.common.core.service.DataSourceService;
import com.hollysys.platform.common.core.toolkit.DataSourceContextHolder;

/**
 * @author lma
 * @date 2020/08/12
 */
public class SwitchDataSourceUtils {

    public static void doSwitch(Runnable task) {
        try {
            DataSourceContextHolder.setDataSourceTag(DataSourceService.BACKUP_DATASOURCE_TAG);
            task.run();
        } finally {
            DataSourceContextHolder.clearDataSource();
        }
    }

}
