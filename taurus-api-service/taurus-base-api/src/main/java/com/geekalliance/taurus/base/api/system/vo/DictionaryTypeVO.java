package com.geekalliance.taurus.base.api.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * DictionaryTypeVO
 *
 * @author geekeeper
 * @email geekkeeper@163.com
 * @date 2021-01-17 20:41
 */
@Data
public class DictionaryTypeVO implements Serializable {

    @ApiModelProperty(name = "主键编号")
    private String id;

    @ApiModelProperty(name = "类型编码")
    private String code;

    @ApiModelProperty(name = "类型名称")
    private String name;

    @ApiModelProperty(name = "备注信息")
    private String remark;

}
