package com.geekalliance.taurus.base.api.organization.dto;

import com.geekalliance.taurus.core.entity.ValidatedGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Copyright(C) 2013-2021 GeekAlliance Inc.ALL Rights Reserved.
 * 组织参数
 * 新增、删除、编辑
 *
 * @author geekeeper
 * @version V1.0.0.0
 * @date 2021-01-10 19:34
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrganizationParam extends ValidatedGroup {

    /**
     * 主键编号
     */
    @ApiModelProperty("主键编号")
    @NotNull(message = "主键编号不能为空，请检查id参数", groups = {update.class, delete.class})
    private String id;

    /**
     * 父级编号
     */
    @ApiModelProperty("父级编号")
    @NotNull(message = "父级编号不能为空，请检查parent参数", groups = {add.class})
    private String parent;

    /**
     * 编码
     */
    @ApiModelProperty("编码")
    @NotBlank(message = "编码不能为空，请检查code参数", groups = {update.class, delete.class})
    private String code;

    /**
     * 机构名称(全称)
     */
    @ApiModelProperty("机构名称(全称)")
    @NotBlank(message = "名称不能为空，请检查name参数", groups = {add.class, update.class})
    private String name;

    /**
     * 机构名称(简称)
     */
    @ApiModelProperty("机构名称(简称)")
    private String shortName;

    /**
     * 图标
     */
    @ApiModelProperty("图标")
    private String icon;

    /**
     * 层级编号
     */
    @ApiModelProperty("层级编号")
    private Integer level;

    /**
     * 序号
     */
    @ApiModelProperty("序号")
    private Integer sortNumber;

    /**
     * 状态
     */
    @ApiModelProperty("状态")
    private Integer statusNumber;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

}
