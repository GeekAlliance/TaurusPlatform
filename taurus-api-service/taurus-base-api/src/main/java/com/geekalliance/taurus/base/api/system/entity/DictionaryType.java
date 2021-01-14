package com.geekalliance.taurus.base.api.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.geekalliance.taurus.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 字典类型
 *
 * @author geekeeper
 * @version V1.0.0.0
 * @date 2021-01-14 20:55
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("base_dictionary_type")
public class DictionaryType extends BaseEntity {
    /**
     * 应用编号
     */
    private String application;

    /**
     * 模块编号
     */
    private String module;

    /**
     * 类型编码
     */
    private String code;

    /**
     * 类型名称
     */
    private String name;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 系统标识
     */
    private String systemFlag;

    /**
     * 删除标识
     */
    private String deleteFlag;
}