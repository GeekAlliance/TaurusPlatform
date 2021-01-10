package com.geekalliance.taurus.base.organization.service;

import com.geekalliance.taurus.base.api.organization.entity.Organization;
import com.geekalliance.taurus.base.organization.mapper.OrganizationMapper;
import com.geekalliance.taurus.rdb.service.RdbService;
import org.springframework.stereotype.Service;
    
/**
 * Copyright(C) 2013-2021 GeekAlliance Inc.ALL Rights Reserved.
 *
 * @author geekeeper
 * @version V1.0.0.0
 * @date 2021-01-10 19:04
 *
 */
@Service
public class OrganizationService extends RdbService<OrganizationMapper,Organization> {

}
