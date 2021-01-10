package com.geekalliance.taurus.base.organization.mapper;

import com.geekalliance.taurus.base.api.organization.entity.Organization;
import com.geekalliance.taurus.rdb.mapper.RdbBaseMapper;
import org.apache.ibatis.annotations.Mapper;

    
/**
 * Copyright(C) 2013-2021 GeekAlliance Inc.ALL Rights Reserved.
 *
 * @author geekeeper
 * @version V1.0.0.0
 * @date 2021-01-10 19:04
 *
 */
@Mapper
public interface OrganizationMapper extends RdbBaseMapper<Organization> {

}