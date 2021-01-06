package com.geekalliance.taurus.base.api.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * @Description
 * @Date 2019/12/18
 * @Author maxuqiang
 **/

@ApiModel
@Data
public class CustomTokenVO {
    @ApiModelProperty(value = "访问Token", example = "访问Token")
    private String accessToken;

    @ApiModelProperty(value = "刷新Token", example = "刷新Token")
    private String refreshToken;

    @ApiModelProperty(value = "Token类型", example = "Token类型")
    private String tokenType;

    @ApiModelProperty(value = "Token有效期", example = "Token有效期")
    private Integer expiresIn;

    @ApiModelProperty(value = "Token作用域", example = "Token作用域")
    private String scope;

    @ApiModelProperty(value = "自定义用户信息", example = "自定义用户信息")
    private Map information;
}
