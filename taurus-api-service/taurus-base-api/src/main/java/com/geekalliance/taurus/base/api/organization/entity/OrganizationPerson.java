package com.geekalliance.taurus.base.api.organization.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.geekalliance.taurus.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 组织人员关系表
 *
 * @author geekeeper
 * @version V1.0.0.0
 * @date 2021-01-10 21:50
 */

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("base_organization_person")
public class OrganizationPerson extends BaseEntity {
    /**
     * 组织编号
     */
    private String organizationId;

    /**
     * 人员编号
     */
    private String personId;
}