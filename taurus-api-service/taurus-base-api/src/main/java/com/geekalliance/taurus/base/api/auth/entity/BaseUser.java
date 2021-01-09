package com.geekalliance.taurus.base.api.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.geekalliance.taurus.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户表
 */
@ApiModel(value = "用户表")
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("base_user")
public class BaseUser extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickName;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 密码类型
     */
    @ApiModelProperty(value = "密码类型")
    private Integer passwordType;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String avatar;

    /**
     * 失效时间
     */
    @ApiModelProperty(value = "失效时间")
    private Date expiredTime;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer status;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 工号
     */
    @ApiModelProperty(value = "工号")
    private String jobNumber;

    /**
     * 生日
     */
    @ApiModelProperty(value = "生日")
    private Date birthday;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 微信
     */
    @ApiModelProperty(value = "微信")
    private String wechat;

    /**
     * 手机
     */
    @ApiModelProperty(value = "手机")
    private String phone;

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    private String telephone;

    /**
     * 备注信息
     */
    @ApiModelProperty(value = "备注信息")
    private String remark;

    /**
     * 数据源
     */
    @ApiModelProperty(value = "数据源")
    private Byte dataSource;

    /**
     * 在线标识
     */
    @ApiModelProperty(value = "在线标识")
    private String onlineFlag;

    /**
     * 启用标识
     */
    @ApiModelProperty(value = "启用标识")
    private String enableFlag;

    /**
     * 超级管理员
     */
    @ApiModelProperty(value = "超级管理员")
    private String superFlag;

    /**
     * 删除标识
     */
    @ApiModelProperty(value = "删除标识")
    private String deleteFlag;
}