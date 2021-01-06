package com.geekalliance.taurus.base.oauth.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.geekalliance.taurus.core.exception.SystemErrorType;
import com.geekalliance.taurus.core.result.Result;
import lombok.EqualsAndHashCode;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
/**
 * @description
 * @date 2019/12/30
 * @author maxuqiang
 **/
@EqualsAndHashCode(callSuper = true)
@JsonSerialize(using = BootOauthExceptionSerializer.class)
public class BootOauthException extends OAuth2Exception {
    private final Result result;

    BootOauthException(OAuth2Exception oAuth2Exception) {
        super(oAuth2Exception.getSummary(), oAuth2Exception);
        this.result = Result.fail(SystemErrorType.valueOf(oAuth2Exception.getOAuth2ErrorCode().toUpperCase()), oAuth2Exception);
    }

    public Result getResult(){
        return result;
    }
}