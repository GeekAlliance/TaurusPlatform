package com.geekalliance.taurus.core.enums;

import lombok.Getter;

/**
 * 标识枚举
 *
 * @author geekeeper
 * @version V1.0.0.0
 * @date 2021-01-11 21:48
 */
@Getter
public enum FlagEnum {
    /**
     * 启用、是
     */
    YES("Y", "是"),
    /**
     * 禁用、否
     */
    NO("N", "否");

    private final String code;
    private final String message;

    FlagEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
