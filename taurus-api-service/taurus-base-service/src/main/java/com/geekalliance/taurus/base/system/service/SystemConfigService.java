package com.geekalliance.taurus.base.system.service;

import com.geekalliance.taurus.base.api.system.params.AddSystemConfigParam;
import com.geekalliance.taurus.core.params.CommonDeleteParam;
import com.geekalliance.taurus.base.api.system.params.UpdateSystemConfigParam;
import com.geekalliance.taurus.base.api.system.entity.SystemConfig;
import com.geekalliance.taurus.base.system.mapper.SystemConfigMapper;
import com.geekalliance.taurus.rdb.service.RdbService;
import com.geekalliance.taurus.toolkit.utils.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author geekeeper
 * @version V1.0.0.0
 * @date 2021-01-14 20:55
 *
 */
@Service
public class SystemConfigService extends RdbService<SystemConfigMapper, SystemConfig> {

    @Resource
    private SystemConfigMapper systemConfigMapper;



    public boolean add(AddSystemConfigParam saveParam) {
        return false;
    }

    public boolean delete(CommonDeleteParam deleteParam) {
        return false;
    }

    public boolean update(List<UpdateSystemConfigParam> updateParam) {
        return ObjectUtils.isEmpty(updateParam);
    }
}