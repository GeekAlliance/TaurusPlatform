package com.geekalliance.taurus.base.api.auth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Date 2019/12/19
 * @Author maxuqiang
 **/
@ApiModel
@Data
public class LoginUserDTO implements Serializable {
    @ApiModelProperty(value = "用户名", example = "用户名", required = true)
    private String username;

    @ApiModelProperty(value = "密码", example = "密码", required = true)
    private String password;
}
