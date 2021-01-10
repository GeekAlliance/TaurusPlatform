package com.geekalliance.taurus.base.api.organization.exception;

import com.geekalliance.taurus.core.exception.ErrorType;
import lombok.Getter;

/**
 * Copyright(C) 2013-2021 GeekAlliance Inc.ALL Rights Reserved.
 *
 * @author geekeeper
 * @version V1.0.0.0
 * @date 2021-01-10 19:38
 */
@Getter
public enum OrganizationErrorType implements ErrorType {

    /**
     * 组织已存在
     */
    ORGANIZATION_EXIST(40010001,"organization.already.existed");

    /**
     * 错误码
     */
    private final int code;
    /**
     * 描述信息
     */
    private final String msg;

    OrganizationErrorType(int code,String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getMsg() {
        return localeMessage.getMessage(msg);
    }
}
