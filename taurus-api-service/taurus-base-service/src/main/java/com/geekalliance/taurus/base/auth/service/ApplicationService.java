package com.geekalliance.taurus.base.auth.service;

import com.geekalliance.taurus.base.api.auth.entity.Application;
import com.geekalliance.taurus.base.auth.mapper.ApplicationMapper;
import com.geekalliance.taurus.rdb.service.RdbService;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService extends RdbService<ApplicationMapper, Application> {

}
