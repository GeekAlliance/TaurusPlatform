package com.geekalliance.taurus.base.oauth.service;


import com.geekalliance.taurus.base.api.auth.entity.BaseUser;
import com.geekalliance.taurus.toolkit.enums.CommonEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author maxuqiang
 */
@Slf4j
public abstract class BaseUserDetailService implements UserDetailsService {
    @Autowired
    private ResourceService resourceService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        BaseUser baseUser = getUser(username);
        // 返回带有用户权限信息的User
        User userDetail =  new org.springframework.security.core.userdetails.User(
                baseUser.getUsername(),
                baseUser.getPassword(),
                isActive(baseUser.getEnableFlag()), true, true, true, this.convertToAuthorities(baseUser));
        return userDetail;
    }

    protected abstract BaseUser getUser(String param) ;

    private boolean isActive(String active){
        if(CommonEnum.NO.equals(active)){
            return false;
        }
        return true;
    }

    private List<GrantedAuthority> convertToAuthorities(BaseUser baseUser) {
        List<String> roles = resourceService.getAuthorities(baseUser.getId());
        List<GrantedAuthority> authorities = new ArrayList();
        roles.forEach(s->{
            GrantedAuthority authority = new SimpleGrantedAuthority(s);
            authorities.add(authority);
        });
        return authorities;
    }
}
