package com.geekalliance.taurus.base.auth.service;

import com.geekalliance.taurus.base.api.auth.entity.Resource;
import com.geekalliance.taurus.base.auth.mapper.ResourceMapper;
import com.geekalliance.taurus.rdb.service.RdbService;
import org.springframework.stereotype.Service;

@Service
public class ResourceService extends RdbService<ResourceMapper, Resource> {

}
