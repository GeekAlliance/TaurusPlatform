package com.geekalliance.taurus.base.api.organization.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 组织机构视图模型
 * Copyright(C) 2013-2021 GeekAlliance Inc.ALL Rights Reserved.
 *
 * @author geekeeper
 * @version V1.0.0.0
 * @date 2021-01-10 19:36
 */
@ApiModel
@Data
public class OrganizationVO implements Serializable {
    /**
     * 机构编码
     */
    @ApiModelProperty(value = "组织编码", example = "组织编码")
    private String code;

    /**
     * 机构名称(简称)
     */
    @ApiModelProperty(value = "机构简称", example = "机构简称")
    private String shortName;

    /**
     * 机构名称(全称)
     */
    @ApiModelProperty(value = "机构全称", example = "机构全称")
    private String fullName;

    /**
     * 机构图标
     */
    @ApiModelProperty(value = "机构图标", example = "机构图标")
    private String icon;

    /**
     * 父级编号
     */
    @ApiModelProperty(value = "父级编号", example = "父级编号")
    private String parent;

    /**
     * 层级编号
     */
    @ApiModelProperty(value = "层级编号", example = "层级编号")
    private Integer level;

    /**
     * 排序编号
     */
    @ApiModelProperty(value = "排序编号", example = "排序编号")
    private Integer sortNumber;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态", example = "状态")
    private Integer statusNumber;

    /**
     * 备注信息
     */
    @ApiModelProperty(value = "备注信息", example = "备注信息")
    private String remark;

    /**
     * 系统标识
     */
    @ApiModelProperty(value = "系统标识", example = "系统标识")
    private String systemFlag;


}
