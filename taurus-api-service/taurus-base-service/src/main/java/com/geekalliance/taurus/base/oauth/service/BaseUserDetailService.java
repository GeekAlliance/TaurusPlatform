package com.geekalliance.taurus.base.oauth.service;


import com.geekalliance.taurus.base.api.auth.entity.BaseUser;
import com.geekalliance.taurus.core.holder.entity.TokenUser;
import com.geekalliance.taurus.core.utils.JwtUtils;
import com.geekalliance.taurus.toolkit.enums.CommonEnum;
import com.geekalliance.taurus.toolkit.utils.JsonUtils;
import com.geekalliance.taurus.toolkit.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.jwt.Jwt;

import java.util.*;

/**
 * @author maxuqiang
 */
@Slf4j
public abstract class BaseUserDetailService implements UserDetailsService {
    @Autowired
    private AuthResourceService authResourceService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        BaseUser baseUser = getUser(username);
        // 返回带有用户权限信息的User
        User userDetail = new org.springframework.security.core.userdetails.User(
                baseUser.getUsername(),
                baseUser.getPassword(),
                isActive(baseUser.getEnableFlag()), true, true, true, this.convertToAuthorities(baseUser));
        return userDetail;
    }

    public TokenUser getTokenUserByToken(String token) {
        if (StringUtils.isNotBlank(token)) {
            authResourceService.tokenValid(token);
            try {
                Jwt jwt = JwtUtils.getJwt(token);
                String jsonStr = jwt.getClaims();
                Map<Object, Object> jo = JsonUtils.serializable(jsonStr, HashMap.class);
                String username = String.valueOf(jo.get("user_name"));
                String clientId = String.valueOf(jo.get("client_id"));
                if (StringUtils.isNotBlank(username)) {
                    TokenUser tokenUser = getTokenUser(username);
                    tokenUser.setClientId(clientId);
                    return tokenUser;
                }
            } catch (RuntimeException ex) {
                log.error("getTokenUserByToken exception " + ex.getMessage());
            }
        }
        return new TokenUser();
    }

    protected abstract BaseUser getUser(String username);

    private boolean isActive(String active) {
        if (CommonEnum.NO.equals(active)) {
            return false;
        }
        return true;
    }

    private List<GrantedAuthority> convertToAuthorities(BaseUser baseUser) {
        List<String> roles = authResourceService.getAuthorities(baseUser.getId());
        List<GrantedAuthority> authorities = new ArrayList();
        roles.forEach(s -> {
            GrantedAuthority authority = new SimpleGrantedAuthority(s);
            authorities.add(authority);
        });
        return authorities;
    }


    private TokenUser getTokenUser(String username) {
        BaseUser user = getUser(username);
        TokenUser tokenUser = convertTokenUser(user);
        return tokenUser;
    }

    private TokenUser convertTokenUser(BaseUser user) {
        TokenUser tokenUser = new TokenUser();
        if (!Objects.isNull(user)) {
            tokenUser.setSuperManage(CommonEnum.YES.getCode().equals(user.getSuperFlag()));
            tokenUser.setId(user.getId());
            tokenUser.setUsername(user.getUsername());
            tokenUser.setAuthorities(new ArrayList());
        }
        return tokenUser;
    }
}
