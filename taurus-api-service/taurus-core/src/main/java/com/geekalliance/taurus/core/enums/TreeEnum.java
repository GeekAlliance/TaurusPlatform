package com.geekalliance.taurus.core.enums;

public enum TreeEnum {

    ROOT_NODE("-1"),

    ROOT_LEVEL("1"),
    ;

    private String code;

    TreeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
