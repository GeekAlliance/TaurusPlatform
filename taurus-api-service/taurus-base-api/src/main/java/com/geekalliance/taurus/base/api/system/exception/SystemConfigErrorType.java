package com.geekalliance.taurus.base.api.system.exception;

import com.geekalliance.taurus.core.exception.ErrorType;
import lombok.Getter;

/**
 * DictionaryErrorType
 *
 * @author geekeeper
 * @email geekkeeper@163.com
 * @date 2021-01-18 20:12
 */
@Getter
public enum SystemConfigErrorType implements ErrorType {

    /**
     * 系统配置编码不能为空
     */
    SYSTEM_CONFIG_CODE_NOT_EMPTY(40010001,"system.config.code.not.empty");


    /**
     * 错误码
     */
    private final int code;
    /**
     * 描述信息
     */
    private final String message;

    SystemConfigErrorType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return localeMessage.getMessage(message);
    }
}
