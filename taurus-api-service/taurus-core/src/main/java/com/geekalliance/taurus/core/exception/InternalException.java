package com.geekalliance.taurus.core.exception;

/**
 * @author maxuqiang
 */
public class InternalException extends BaseException {
    public InternalException() {
    }

    public InternalException(ErrorType errorType) {
        super(errorType);
    }

    public InternalException(ErrorType errorType, String message) {
        super(errorType, message);
    }

    public InternalException(ErrorType errorType, String message, Throwable cause) {
        super(errorType, message, cause);
    }
}
