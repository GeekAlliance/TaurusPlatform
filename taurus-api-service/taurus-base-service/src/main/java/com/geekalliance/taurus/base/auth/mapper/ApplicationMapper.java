package com.geekalliance.taurus.base.auth.mapper;

import com.geekalliance.taurus.base.api.auth.entity.Application;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApplicationMapper extends com.geekalliance.taurus.rdb.mapper.RdbBaseMapper<Application> {
}