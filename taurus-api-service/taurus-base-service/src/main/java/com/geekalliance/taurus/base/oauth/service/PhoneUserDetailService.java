package com.geekalliance.taurus.base.oauth.service;


import com.geekalliance.taurus.base.api.auth.entity.User;
import org.springframework.stereotype.Service;


/**
 * @author maxuqiang
 */
@Service
public class PhoneUserDetailService extends BaseUserDetailService {

    @Override
    protected User getUser(String phone) {
        return null;
    }
}
