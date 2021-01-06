package com.geekalliance.taurus.base.exception;


import com.geekalliance.taurus.base.api.auth.exception.AuthErrorType;
import com.geekalliance.taurus.base.oauth.exception.ExceptionMessage;
import com.geekalliance.taurus.core.exception.SystemErrorType;
import com.geekalliance.taurus.core.result.Result;
import com.geekalliance.taurus.web.exception.DefaultGlobalExceptionHandlerAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author maxuqiang
 * @description
 * @date 2019/12/30
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandlerAdvice extends DefaultGlobalExceptionHandlerAdvice {
    @ExceptionHandler(value = {AccessDeniedException.class})
    public Result accessDeniedException(AccessDeniedException e) {
        return Result.fail(SystemErrorType.ACCESS_DENIED);
    }

    @ExceptionHandler(value = {UsernameNotFoundException.class})
    public Result usernameNotFoundException(UsernameNotFoundException e) {
        return Result.fail(AuthErrorType.USERNAME_NOT_FOUND);
    }

    @ExceptionHandler(value = {InvalidGrantException.class})
    public Result invalidGrantException(InvalidGrantException e) {
        if (e.getMessage().contains(ExceptionMessage.INVALID_REFRESH_TOKEN.getCode())) {
            return Result.fail(SystemErrorType.INVALID_TOKEN);
        }
        if (e.getMessage().contains(ExceptionMessage.BAD_CREDENTIALS.getCode())) {
            return Result.fail(SystemErrorType.BAD_CREDENTIALS);
        }
        return Result.fail(SystemErrorType.INVALID_GRANT);
    }

}