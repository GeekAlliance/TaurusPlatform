package com.geekalliance.taurus.base.api.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.geekalliance.taurus.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色表
 */
@ApiModel(value = "角色表")
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("base_role")
public class BaseRole extends BaseEntity {
    /**
     * 角色编码
     */
    @ApiModelProperty(value = "角色编码")
    private String code;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    private String name;

    /**
     * 排序编号
     */
    @ApiModelProperty(value = "排序编号")
    private Short sort;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Byte status;

    /**
     * 备注信息
     */
    @ApiModelProperty(value = "备注信息")
    private String remark;

    /**
     * 删除标识
     */
    @ApiModelProperty(value = "删除标识")
    private String deleteFlag;
}