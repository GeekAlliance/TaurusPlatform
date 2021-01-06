package com.geekalliance.taurus.base.oauth.exception;

import lombok.Data;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
/**
 * @description
 * @date 2019/12/30
 * @author maxuqiang
 **/
@Data
public class CustomUserNameNotFoundException extends OAuth2Exception {

    public CustomUserNameNotFoundException(String msg) {
        super(msg);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return "username_not_found";
    }
}