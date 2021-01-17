package com.geekalliance.taurus.rdb.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author maxuqiang
 */
public interface DataSourceService {
    /**
     * 多线程中对DATA_SOURCES 操作需要先获取到锁，初始化数据源和清理数据源的时候
     */
    Object LOCK = new Object();

    String MASTER_DATASOURCE_TAG = "master";

    /**
     * 初始化数据源
     */
    Map<Object,Object> initDataSource();

    boolean cleanNotExistDataSource(Object dataSourceTag);

    Set<Object> getDataSourceKeys();

    String getCurrentDbUrl();
}
