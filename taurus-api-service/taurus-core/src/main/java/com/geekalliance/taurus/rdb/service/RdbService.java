package com.geekalliance.taurus.rdb.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.geekalliance.taurus.core.utils.BeanGenerator;
import com.geekalliance.taurus.rdb.mapper.RdbBaseMapper;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class RdbService<M extends RdbBaseMapper<T>, T> extends ServiceImpl<M, T> {
    protected final static int MAX_SUPPORT_PARAMS = 2009;

    @Value("${mybatis-plus.global-config.db-config.logic-delete-value}")
    protected String logicDeleteValue;

    @Value("${mybatis-plus.global-config.db-config.logic-not-delete-value}")
    protected String logicNotDeleteValue;

    @Autowired
    protected BeanGenerator beanGenerator;

    @Autowired
    protected DataSourceService dataSourceService;

    @Autowired
    protected SqlSessionTemplate sqlSessionTemplate;

}
