package com.geekalliance.taurus.base.api.auth.enums;

/**
 * @description
 * @date 2019/12/25
 * @author maxuqiang
 **/

public enum GrantTypeEnum {
    PASSWORD("password"),
    REFRESH_TOKEN("refresh_token"),
    CLIENT_CREDENTIALS("client_credentials"),
    AUTHORIZATION_CODE("authorization_code"),
    IMPLICIT("implicit"),
    ;
    private final String code;

    GrantTypeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
