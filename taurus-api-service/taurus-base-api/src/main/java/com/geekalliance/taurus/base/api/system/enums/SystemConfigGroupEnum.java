package com.geekalliance.taurus.base.api.system.enums;

/**
 * SystemConfigGroupEnum
 * 配置分组枚举
 *
 * @author geekeeper
 * @email geekkeeper@163.com
 * @date 2021-01-14 22:40
 */
public enum SystemConfigGroupEnum {

    /**
     * 安全设置
     */
    SECURITY_SETTING("security_setting"),
    /**
     * 个性化设置
     */
    PERSONALIZATION("personalization");

    private final String code;

    SystemConfigGroupEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
