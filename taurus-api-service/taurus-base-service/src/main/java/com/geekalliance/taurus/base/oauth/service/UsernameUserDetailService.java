package com.geekalliance.taurus.base.oauth.service;


import com.geekalliance.taurus.base.api.auth.entity.User;
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
    public User getUser(String username) {
        User user = null;
        if (user == null) {
            if (user == null) {
                throw new UsernameNotFoundException("username not found exception " + username);
            }
        }
        return user;
    }
}
