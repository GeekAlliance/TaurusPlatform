package com.geekalliance.taurus.base.api.organization.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.geekalliance.taurus.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 人员岗位关系表
 *
 * @author geekeeper
 * @version V1.0.0.0
 * @date 2021-01-10 21:50
 */

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("base_person_post")
public class PersonPost extends BaseEntity {
    /**
     * 人员编号
     */
    private String personId;

    /**
     * 岗位编号
     */
    private String postId;
}