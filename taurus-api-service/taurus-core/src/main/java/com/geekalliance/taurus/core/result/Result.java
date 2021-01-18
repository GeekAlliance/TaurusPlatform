package com.geekalliance.taurus.core.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geekalliance.taurus.core.exception.BaseException;
import com.geekalliance.taurus.core.exception.ErrorType;
import com.geekalliance.taurus.core.exception.SystemErrorType;
import com.geekalliance.taurus.toolkit.StringPool;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author maxuqiang
 */
@ApiModel
@Getter
public final class Result<T> extends CommonResult {
    public static final int SUCCESSFUL_CODE = 1;
    public static final String SUCCESSFUL_MSG = "success";

    @ApiModelProperty(value = "返回内容")
    private T data;

    public Result() {
    }

    public Result(ErrorType errorType) {
        this.code = errorType.getCode();
        this.msg = errorType.getMessage() + StringPool.LEFT_BRACKET + errorType.getCode() + StringPool.RIGHT_BRACKET;
    }

    private Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(ErrorType errorType, T data) {
        this(errorType);
        this.data = data;
    }

    public static Result success(Object data) {
        return new Result<>(SUCCESSFUL_CODE, SUCCESSFUL_MSG, data);
    }

    public static Result success() {
        return success(null);
    }

    public static Result fail() {
        return new Result(SystemErrorType.SYSTEM_ERROR);
    }

    public static Result fail(BaseException baseException, Object data) {
        return new Result<>(baseException.getErrorType(), data);
    }

    public static Result fail(ErrorType errorType, Object data) {
        return new Result<>(errorType, data);
    }

    public static Result fail(ErrorType errorType) {
        return Result.fail(errorType, null);
    }

    public static Result fail(Object data) {
        return new Result<>(SystemErrorType.SYSTEM_ERROR, data);
    }

    @JsonIgnore
    public boolean isSuccess() {
        return SUCCESSFUL_CODE == this.code;
    }

    public Result setMsg(String msg) {
        if (StringUtils.isNotBlank(this.msg)) {
            this.msg += msg;
        }
        return this;
    }

    @JsonIgnore
    public boolean isFail() {
        return !isSuccess();
    }
}
