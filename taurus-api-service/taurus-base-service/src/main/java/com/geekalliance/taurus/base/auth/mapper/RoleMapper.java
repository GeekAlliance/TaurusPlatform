package com.geekalliance.taurus.base.auth.mapper;

import com.geekalliance.taurus.base.api.auth.entity.Role;
import com.geekalliance.taurus.rdb.mapper.RdbBaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper extends RdbBaseMapper<Role> {
}