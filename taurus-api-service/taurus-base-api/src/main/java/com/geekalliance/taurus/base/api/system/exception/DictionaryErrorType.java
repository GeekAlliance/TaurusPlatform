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
public enum DictionaryErrorType implements ErrorType {

    /**
     * 字典类型不能为空
     */
    DICTIONARY_TYPE_NOT_EMPTY(40010001,"dictionary.type.not.empty"),
    TYPE_ID_OR_CODE_CANNOT_BE_EMPTY_AT_THE_SAME_TIME(40010002,"type.id.or.code.cannot.be.empty.at.the.same.time");


    /**
     * 错误码
     */
    private final int code;
    /**
     * 描述信息
     */
    private final String message;

    DictionaryErrorType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return localeMessage.getMessage(message);
    }
}
