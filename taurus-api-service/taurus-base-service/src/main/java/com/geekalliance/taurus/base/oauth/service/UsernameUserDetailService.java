package com.geekalliance.taurus.base.oauth.service;


import com.geekalliance.taurus.base.api.auth.entity.BaseUser;
import com.geekalliance.taurus.base.service.BaseUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author maxuqiang
 */
@Slf4j
@Service
public class UsernameUserDetailService extends BaseUserDetailService {
    @Autowired
    private BaseUserService baseUserService;

    @Override
    public BaseUser getUser(String username) {
        BaseUser baseUser = baseUserService.getBaseUserByUserName(username);
        if (baseUser == null) {
            throw new UsernameNotFoundException("username not found exception " + username);
        }
        return baseUser;
    }
}
