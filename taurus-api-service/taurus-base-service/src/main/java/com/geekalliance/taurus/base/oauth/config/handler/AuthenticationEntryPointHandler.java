package com.geekalliance.taurus.base.oauth.config.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.geekalliance.taurus.base.oauth.exception.ExceptionMessage;
import com.geekalliance.taurus.core.exception.SystemErrorType;
import com.geekalliance.taurus.core.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author maxuqiang
 */
@Component
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint, Serializable {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        allowCors(response);
        response.setContentType("application/json");
        if(e instanceof InsufficientAuthenticationException){
            if(e.getMessage().contains(ExceptionMessage.INVALID_ACCESS_TOKEN.getCode())){
                objectMapper.writeValue(response.getOutputStream(), Result.fail(SystemErrorType.INVALID_TOKEN));
            }else{
                objectMapper.writeValue(response.getOutputStream(), Result.fail(SystemErrorType.ACCESS_DENIED));
            }
        }else{
            objectMapper.writeValue(response.getOutputStream(), Result.fail(e.getMessage()));
        }
    }

    public void allowCors(HttpServletResponse response) throws IOException, ServletException {
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,"*");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "false");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "*");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "-1");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "*");
    }
}
