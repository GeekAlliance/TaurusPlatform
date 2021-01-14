package com.geekalliance.taurus.base.api.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.geekalliance.taurus.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 系统配置表
 *
 * @author geekeeper
 * @version V1.0.0.0
 * @date 2021-01-14 20:55
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("base_system_config")
public class SystemConfig extends BaseEntity {
    /**
     * 应用编号
     */
    private String application;

    /**
     * 模块编号
     */
    private String module;

    /**
     * 配置组
     */
    private String subgroup;

    /**
     * 配置编码
     */
    private String code;

    /**
     * 配置名称
     */
    private String name;

    /**
     * 配置值
     */
    private String configValue;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 状态
     */
    private Integer statusNumber;

    /**
     * 系统标识
     */
    private String systemFlag;

    /**
     * 删除标识
     */
    private String deleteFlag;
}