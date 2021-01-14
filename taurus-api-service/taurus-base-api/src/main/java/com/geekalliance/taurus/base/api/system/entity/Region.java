package com.geekalliance.taurus.base.api.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.geekalliance.taurus.core.entity.BaseEntity;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

    
/**
 *行政区划表
 * @author geekeeper
 * @version V1.0.0.0
 * @date 2021-01-14 20:55
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName("base_region")
public class Region extends BaseEntity {
    /**
    * 编码
    */
    private String code;

    /**
    * 名称
    */
    private String name;

    /**
    * 区域类型
    */
    private Integer type;

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