package com.geekalliance.taurus.base.oauth.exception;

public enum ExceptionMessage {
    INVALID_ACCESS_TOKEN("Invalid access token"),
    INVALID_REFRESH_TOKEN("Invalid refresh token"),
    BAD_CREDENTIALS("Bad credentials"),
    ;
    private String code;

    ExceptionMessage(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
