package com.geekalliance.taurus.base.system.service;

import com.geekalliance.taurus.base.api.system.entity.Dictionary;
import com.geekalliance.taurus.base.system.mapper.DictionaryMapper;
import com.geekalliance.taurus.base.system.mapper.OperateLogMapper;
import com.geekalliance.taurus.rdb.service.RdbService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.geekalliance.taurus.base.api.system.entity.OperateLog;
    
/**
 * OperateLogService
 * @author geekeeper
 * @version V1.0.0.0
 * @date 2021-01-14 20:55
 *
 */
@Service
public class OperateLogService extends RdbService<OperateLogMapper, OperateLog> {

    @Resource
    private OperateLogMapper operateLogMapper;


}
