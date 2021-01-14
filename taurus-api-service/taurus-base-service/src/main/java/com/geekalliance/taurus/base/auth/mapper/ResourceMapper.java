package com.geekalliance.taurus.base.auth.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.geekalliance.taurus.base.api.auth.entity.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ResourceMapper extends com.geekalliance.taurus.rdb.mapper.RdbBaseMapper<Resource> {
    List<Resource> selectListByUserPermission(@Param("ew") QueryWrapper<Resource> queryWrapper);
}