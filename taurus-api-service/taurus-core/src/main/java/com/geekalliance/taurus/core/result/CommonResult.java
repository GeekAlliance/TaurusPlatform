package com.geekalliance.taurus.core.result;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


public class CommonResult implements Serializable {

    @ApiModelProperty(value = "状态码", example = "状态码")
    protected int code;

    @ApiModelProperty(value = "提示信息" , example = "提示信息")
    protected String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
