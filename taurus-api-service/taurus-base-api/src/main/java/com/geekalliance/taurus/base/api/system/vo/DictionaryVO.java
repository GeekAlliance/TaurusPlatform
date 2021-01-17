package com.geekalliance.taurus.base.api.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * DictionaryVO
 *
 * @author geekeeper
 * @email geekkeeper@163.com
 * @date 2021-01-17 21:09
 */
@Data
public class DictionaryVO implements Serializable {

    @ApiModelProperty(name = "主键编号")
    private String id;

    @ApiModelProperty(name = "字典类型")
    private String typeId;

    @ApiModelProperty(name = "字典类型")
    private String typeCode;

    @ApiModelProperty(name = "字典编码")
    private Integer code;

    @ApiModelProperty(name = "字典值")
    private String dictionaryValue;

    @ApiModelProperty(name = "备注信息")
    private String remark;

    @ApiModelProperty(name = "系统标识")
    private String systemFlag;


}
