package com.geekalliance.taurus.base.api.auth.enums;

/**
 * @Description
 * @Date 2019/12/25
 * @Author maxuqiang
 **/

public enum PasswordTypeEnum {
    INIT_PASSWORD(0),
    CUSTOM_PASSWORD(1),
    ;
    private final Integer code;

    PasswordTypeEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }
}
