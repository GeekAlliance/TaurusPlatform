package com.geekalliance.taurus.base.api.auth.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.geekalliance.taurus.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

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
    /**
     * 昵称
     */
    private String nickName;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 失效时间
     */
    private Date expiredTime;

    /**
     * 状态
     */
    private Byte status;

    /**
     * 名称
     */
    private String name;

    /**
     * 工号
     */
    private String jobNumber;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 微信
     */
    private String wechat;

    /**
     * 手机
     */
    private String phone;

    /**
     * 电话
     */
    private String telephone;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 数据源
     */
    private Byte dataSource;

    /**
     * 在线标识
     */
    private String onlineFlag;

    /**
     * 启用标识
     */
    private String enableFlag;

    /**
     * 超级管理员
     */
    private String superFlag;

    /**
     * 密码类型
     */
    private String passwordType;

    /**
     * 删除标识
     */
    private String deleteFlag;

    /**
     * 创建时间
     */
    private Date createOn;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 修改时间
     */
    private Date updateOn;

    /**
     * 修改人
     */
    private String updateBy;
}
