package com.geekalliance.taurus.base.mapper;

import com.geekalliance.taurus.base.api.auth.entity.BaseUser;import com.geekalliance.taurus.rdb.mapper.RdbBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BaseUserMapper extends RdbBaseMapper<BaseUser> {
}