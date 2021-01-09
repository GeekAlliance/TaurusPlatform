package com.geekalliance.taurus.base.service;

import com.geekalliance.taurus.base.api.auth.entity.BaseUser;
import com.geekalliance.taurus.base.mapper.BaseUserMapper;
import com.geekalliance.taurus.rdb.service.RdbService;
import org.springframework.stereotype.Service;

@Service
public class BaseUserService extends RdbService<BaseUserMapper, BaseUser> {
    public BaseUser getSysUserByUserName(String username) {
        return this.baseMapper.getSysUserByUserName(username, logicNotDeleteValue);
    }
}
