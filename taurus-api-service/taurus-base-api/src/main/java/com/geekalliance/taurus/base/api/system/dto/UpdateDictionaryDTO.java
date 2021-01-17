package com.geekalliance.taurus.base.api.system.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * UpdateDictionaryDTO
 *
 * @author geekeeper
 * @email geekkeeper@163.com
 * @date 2021-01-17 21:27
 */
@Data
public class UpdateDictionaryDTO implements Serializable {

    @ApiModelProperty(value = "主键编号", example = "主键编号")
    @NotBlank(message = "主键编号不能为空，请检查id参数")
    private String id;

    @ApiModelProperty(name = "字典值")
    private String dictionaryValue;

    @ApiModelProperty(name = "备注信息")
    private String remark;

}
