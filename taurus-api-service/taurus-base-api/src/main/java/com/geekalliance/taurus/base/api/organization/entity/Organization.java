package com.geekalliance.taurus.base.api.organization.entity;

import com.geekalliance.taurus.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * Copyright(C) 2013-2021 GeekAlliance Inc.ALL Rights Reserved.
 * 组织机构表
 *
 * @author geekeeper
 * @version V1.0.0.0
 * @date 2021-01-10 19:04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Organization extends BaseEntity {

    /**
     * 机构编码
     */
    private String code;

    /**
     * 机构名称(全称)
     */
    private String name;

    /**
     * 机构名称(简称)
     */
    private String shortName;

    /**
     * 机构图标
     */
    private String icon;

    /**
     * 父级编号
     */
    private String parent;

    /**
     * 层级编号
     */
    private Integer level;

    /**
     * 排序编号
     */
    private Integer sortNumber;

    /**
     * 状态
     */
    private Integer statusNumber;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 数据源
     */
    private Integer dataSource;

    /**
     * 系统标识
     */
    private String systemFlag;

    /**
     * 删除标识
     */
    private String deleteFlag;

}