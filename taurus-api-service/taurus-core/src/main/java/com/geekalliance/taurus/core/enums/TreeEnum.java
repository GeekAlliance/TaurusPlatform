package com.geekalliance.taurus.core.enums;

/**
 * 树根节点枚举
 * @author geekeeper
 */
public enum TreeEnum {

    /**
     * 根节点
     */
    ROOT_NODE("-1"),

    /**
     * 层级
     */
    ROOT_LEVEL("1"),
    ;

    private final String code;

    TreeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
