package com.geekalliance.taurus.base.api.auth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @Description
 * @Date 2019/12/17
 * @Author maxuqiang
 **/
@ApiModel
@Data
public class SaveBaseUserDTO implements Serializable {

    @NotEmpty(message = "用户登录名不能为空")
    @Pattern(regexp = "(^\\d{0}$)|(^[a-zA-Z0-9_]{3,64}$)", message = "用户登录名只能包含3到64位数字、字母、下划线")
    @ApiModelProperty(value = "用户登录名", example = "用户登录名只能包含3到64位数字、字母、下划线", required = true)
    private String username;

}
