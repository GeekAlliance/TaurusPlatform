package com.geekalliance.taurus.base.api.auth.enums;

public enum ResourceTypeEnum {
    MODULE(1),
    ACTION(2),
    ;
    private final int code;

    ResourceTypeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
