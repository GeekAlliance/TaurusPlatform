package com.geekalliance.taurus.base.auth.mapper;

import com.geekalliance.taurus.base.api.auth.entity.Resource;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ResourceMapper extends com.geekalliance.taurus.rdb.mapper.RdbBaseMapper<Resource> {
}