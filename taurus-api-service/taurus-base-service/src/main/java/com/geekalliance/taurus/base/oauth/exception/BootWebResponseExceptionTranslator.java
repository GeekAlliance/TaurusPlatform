package com.geekalliance.taurus.base.oauth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

/**
 * @description
 * @date 2019/12/30
 * @author maxuqiang
 **/
public class BootWebResponseExceptionTranslator implements WebResponseExceptionTranslator {

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) {
        if(e instanceof UsernameNotFoundException){
            CustomUserNameNotFoundException oAuth2Exception = new CustomUserNameNotFoundException("username_not_found");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new BootOauthException(oAuth2Exception));
        }else{
            OAuth2Exception oAuth2Exception = (OAuth2Exception) e;
            return ResponseEntity.status(oAuth2Exception.getHttpErrorCode())
                    .body(new BootOauthException(oAuth2Exception));
        }
    }
}
