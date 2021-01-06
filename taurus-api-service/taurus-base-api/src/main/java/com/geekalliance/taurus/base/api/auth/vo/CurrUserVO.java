package com.geekalliance.taurus.base.api.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Date 2019/12/30
 * @Author maxuqiang
 **/
@ApiModel
@Data
public class CurrUserVO implements Serializable {
    @ApiModelProperty(value = "用户编号", example = "用户编号")
    private String id;

    @ApiModelProperty(value = "外部系统唯一编号", example = "外部系统唯一编号")
    private String externId;

    @ApiModelProperty(value = "用户登陆名", example = "用户登陆名")
    private String username;

    @ApiModelProperty(value = "昵称", example = "昵称")
    private String nickName;

    @ApiModelProperty(value = "工号", example = "工号")
    private String workNo;

    @ApiModelProperty(value = "邮箱", example = "邮箱")
    private String email;

    @ApiModelProperty(value = "微信号", example = "微信号")
    private String wechat;

    @ApiModelProperty(value = "手机号", example = "手机号")
    private String phone;

    @ApiModelProperty(value = "用户类型", example = "用户类型")
    private String userType;

    @ApiModelProperty(value = "状态", example = "用户状态")
    private String status;

    @ApiModelProperty(value = "用户姓名", example = "用户姓名")
    private String personName;

    @ApiModelProperty(value = "是否超级管理员", example = "Y")
    private String isSuper;

    @ApiModelProperty(value = "用户角色")
    private List<CurrUserRoleVO> roles;
}
