package com.geekalliance.taurus.toolkit.enums;

/**
 * @author maxuqiang
 */

public enum CommonEnum {
    /**
     * 通用枚举
     */
    YES("Y"),
    NO("N"),
    ;
    private final String code;

    CommonEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
