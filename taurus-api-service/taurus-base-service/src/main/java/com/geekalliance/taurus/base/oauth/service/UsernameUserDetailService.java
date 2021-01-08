package com.geekalliance.taurus.base.oauth.service;


import com.geekalliance.taurus.base.api.auth.entity.BaseUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author maxuqiang
 */
@Slf4j
@Service
public class UsernameUserDetailService extends BaseUserDetailService {

    @Override
    public BaseUser getUser(String username) {
        BaseUser baseUser = null;
        if (baseUser == null) {
            if (baseUser == null) {
                throw new UsernameNotFoundException("username not found exception " + username);
            }
        }
        return baseUser;
    }
}
