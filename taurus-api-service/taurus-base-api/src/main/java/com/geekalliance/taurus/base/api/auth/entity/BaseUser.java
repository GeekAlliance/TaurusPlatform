package com.geekalliance.taurus.base.api.auth.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.geekalliance.taurus.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 *
 * @author Gen Code
 * @since 2019-12-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("BASE_USER")
public class BaseUser extends BaseEntity {

    private static final long serialVersionUID=1L;

    @TableId(value = "ID",type = IdType.ASSIGN_UUID)
    private String id;

    private String externId;

    private String username;

    private String password;

    private String departName;

    private String nickName;

    private String workNo;

    private String email;

    private String wechat;

    private String phone;

    private String online;

    private String enable;

    private String statusCode;

    private String isSuper;

    private String personName;

    private String passwordType;

    private String typeCode;

    @TableLogic
    private String deleteTag;
}
