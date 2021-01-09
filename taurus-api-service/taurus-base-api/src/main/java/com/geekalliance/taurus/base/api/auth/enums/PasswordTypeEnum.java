package com.geekalliance.taurus.base.api.auth.enums;

/**
 * @Description
 * @Date 2019/12/25
 * @Author maxuqiang
 **/

public enum PasswordTypeEnum {
    INIT_PASSWORD("0"),
    CUSTOM_PASSWORD("1"),
    ;
    private String code;

    PasswordTypeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
