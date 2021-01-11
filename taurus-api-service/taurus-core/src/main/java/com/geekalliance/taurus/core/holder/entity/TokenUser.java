package com.geekalliance.taurus.core.holder.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author maxuqiang
 * @description
 * @date 2019/12/23
 **/
@Data
@ApiModel(description = "登录用户信息")
public class TokenUser implements Serializable {
    @ApiModelProperty(value = "用户编号", example = "用户编号")
    private String id;

    @ApiModelProperty(value = "用户名", example = "用户名")
    private String username;

    @ApiModelProperty(value = "客户端编号", example = "客户端编号")
    private String clientId;

    @ApiModelProperty(value = "当前登录用户角色ID", example = "当前登录用户角色ID")
    private List<String> authorities;

    @ApiModelProperty(value = "是否超级管理员", example = "是否超级管理员")
    private boolean isSuper = false;

    public TokenUser() {

    }
}
