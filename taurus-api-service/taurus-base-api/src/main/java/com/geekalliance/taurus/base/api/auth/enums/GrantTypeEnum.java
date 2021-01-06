package com.geekalliance.taurus.base.api.auth.enums;

/**
 * @Description
 * @Date 2019/12/25
 * @Author maxuqiang
 **/

public enum GrantTypeEnum {
    PASSWORD("password"),
    REFRESH_TOKEN("refresh_token"),
    CLIENT_CREDENTIALS("client_credentials"),
    AUTHORIZATION_CODE("authorization_code"),
    IMPLICIT("implicit"),
    ;
    private String code;

    GrantTypeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
