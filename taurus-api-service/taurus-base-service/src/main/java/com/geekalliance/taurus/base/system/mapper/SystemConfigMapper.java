package com.geekalliance.taurus.base.system.mapper;

import com.geekalliance.taurus.base.api.system.entity.SystemConfig;
import com.geekalliance.taurus.rdb.mapper.RdbBaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 * SystemConfigMapper
 *
 * @author geekeeper
 * @version V1.0.0.0
 * @date 2021-01-14 20:55
 */
@Mapper
public interface SystemConfigMapper extends RdbBaseMapper<SystemConfig> {

}