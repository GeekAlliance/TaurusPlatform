package com.geekalliance.taurus.core.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private ErrorType errorType;

    public BaseException() {
        super(SystemErrorType.SYSTEM_ERROR.getMsg());
        this.errorType = SystemErrorType.SYSTEM_ERROR;
    }

    public BaseException(ErrorType errorType) {
        super(errorType.getMsg());
        this.errorType = errorType;
    }

    public BaseException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

    public BaseException(ErrorType errorType, String message, Throwable cause) {
        super(message, cause);
        this.errorType = errorType;
    }

    public BaseException(String message) {
        super(message);
    }
}
