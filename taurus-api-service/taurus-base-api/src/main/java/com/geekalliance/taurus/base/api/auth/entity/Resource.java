package com.geekalliance.taurus.base.api.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.geekalliance.taurus.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 资源表
 */
@ApiModel(value = "资源表")
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("base_resource")
public class Resource extends BaseEntity {
    /**
     * 应用编码
     */
    @ApiModelProperty(value = "应用编码")
    private String application;

    /**
     * 编码
     */
    @ApiModelProperty(value = "编码")
    private String code;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    private Integer type;

    /**
     * 图标
     */
    @ApiModelProperty(value = "图标")
    private String icon;

    /**
     * 路由地址
     */
    @ApiModelProperty(value = "路由地址")
    private String route;

    /**
     * 组件地址
     */
    @ApiModelProperty(value = "组件地址")
    private String component;

    /**
     * 链接地址
     */
    @ApiModelProperty(value = "链接地址")
    private String link;

    /**
     * 重定向地址
     */
    @ApiModelProperty(value = "重定向地址")
    private String redirect;

    /**
     * 备注信息
     */
    @ApiModelProperty(value = "备注信息")
    private String remark;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer statusNumber;

    /**
     * 父级
     */
    @ApiModelProperty(value = "父级")
    private String parent;

    /**
     * 层级
     */
    @ApiModelProperty(value = "层级")
    private Integer level;

    /**
     * 序号
     */
    @ApiModelProperty(value = "序号")
    private Integer sortNumber;

    /**
     * 权限标识
     */
    @ApiModelProperty(value = "权限标识")
    private String permitFlag;

    /**
     * 可见标识
     */
    @ApiModelProperty(value = "可见标识")
    private String visibleFlag;

    /**
     * 启用标识
     */
    @ApiModelProperty(value = "启用标识")
    private String enableFlag;

    /**
     * 删除标识
     */
    @ApiModelProperty(value = "删除标识")
    private String deleteFlag;
}