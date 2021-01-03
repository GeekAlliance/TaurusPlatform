package com.geekalliance.taurus.core.exception;

/**
 * @author maxuqiang
 */
public class ApiException extends BaseException {
    public ApiException() {
        super();
    }

    public ApiException(ErrorType errorType) {
        super(errorType);
    }

    public ApiException(ErrorType errorType, String message) {
        super(errorType,message);
    }

    public ApiException(ErrorType errorType, String message, Throwable cause) {
        super(errorType,message, cause);
    }
}
