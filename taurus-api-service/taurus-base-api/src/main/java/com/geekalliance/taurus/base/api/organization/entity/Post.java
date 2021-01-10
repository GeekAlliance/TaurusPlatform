package com.geekalliance.taurus.base.api.organization.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.geekalliance.taurus.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/*
  Copyright(C) 2013-2021 GeekAlliance Inc.ALL Rights Reserved.
 */

/**
 * 岗位表
 *
 * @author geekeeper
 * @version V1.0.0.0
 * @date 2021-01-10 19:04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("base_post")
public class Post extends BaseEntity {
    /**
     *
     */
    private String id;

    /**
     * 岗位编码
     */
    private String code;

    /**
     * 岗位名称
     */
    private String name;

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
     * 删除标识
     */
    private String deleteFlag;

}
