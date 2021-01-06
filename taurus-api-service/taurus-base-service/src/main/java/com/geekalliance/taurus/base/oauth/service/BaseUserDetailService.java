package com.geekalliance.taurus.base.oauth.service;


import com.geekalliance.taurus.base.api.auth.entity.User;
import com.geekalliance.taurus.toolkit.enums.CommonEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

        User user = getUser(username);
        // 返回带有用户权限信息的User
        org.springframework.security.core.userdetails.User userDetail =  new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                isActive(user.getEnable()), true, true, true, this.convertToAuthorities(user));
        return userDetail;
    }

    protected abstract User getUser(String param) ;

    private boolean isActive(String active){
        if(CommonEnum.NO.equals(active)){
            return false;
        }
        return true;
    }

    private List<GrantedAuthority> convertToAuthorities(User user) {
        List<String> roles = resourceService.getAuthorities(user.getId());
        List<GrantedAuthority> authorities = new ArrayList();
        roles.forEach(s->{
            GrantedAuthority authority = new SimpleGrantedAuthority(s);
            authorities.add(authority);
        });
        return authorities;
    }
}
