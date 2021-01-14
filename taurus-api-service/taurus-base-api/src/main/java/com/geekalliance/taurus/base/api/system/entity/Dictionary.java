package com.geekalliance.taurus.base.api.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.geekalliance.taurus.core.entity.BaseEntity;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

    
/**
 *字典表
 * @author geekeeper
 * @version V1.0.0.0
 * @date 2021-01-14 20:55
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName("base_dictionary")
public class Dictionary extends BaseEntity {
    /**
    * 字典类型
    */
    private String typeId;

    /**
    * 字典类型
    */
    private String typeCode;

    /**
    * 字典编码
    */
    private Integer code;

    /**
    * 字典值
    */
    private String dictionaryValue;

    /**
    * 备注信息
    */
    private String remark;

    /**
    * 序号
    */
    private Integer sortNumber;

    /**
    * 系统标识
    */
    private String systemFlag;

    /**
    * 删除标识
    */
    private String deleteFlag;
}