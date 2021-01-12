package com.geekalliance.taurus.base.oauth.service;

import com.geekalliance.taurus.core.exception.InternalException;
import com.geekalliance.taurus.core.exception.SystemErrorType;
import com.geekalliance.taurus.toolkit.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 提供权限验证时用户、角色、行为、模块列表等资源信息的查询
 *
 * @author maxuqiang
 */
@Service
@Slf4j
public class AuthResourceService {
    @Autowired
    private TokenStore tokenStore;
    /**
     * 根据当前用户 查询角色编号
     *
     * @param id
     * @return
     */
    public List<String> getAuthorities(String id) {
        List<String> authorities = new ArrayList<>();
        return authorities;
    }

    public boolean tokenValid(String token) {
        if (!StringUtils.isNotBlank(token)) {
            log.error("token is blank");
            return false;
        }
        try {
            OAuth2AccessToken result = tokenStore.readAccessToken(token);
            if (Objects.isNull(result)) {
                throw new InternalException(SystemErrorType.INVALID_TOKEN);
            }
            if (!Objects.isNull(result) && result.isExpired()) {
                throw new InternalException(SystemErrorType.EXPIRED_TOKEN);
            }
        } catch (OAuth2Exception e) {
            throw new InternalException(SystemErrorType.valueOf(e.getOAuth2ErrorCode().toUpperCase()));
        }
        return true;
    }
}
