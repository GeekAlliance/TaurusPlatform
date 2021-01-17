package com.geekalliance.taurus.base.api.system.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * DictionaryQueryDTO
 *
 * @author geekeeper
 * @email geekkeeper@163.com
 * @date 2021-01-17 20:18
 */
@Data
public class QueryDictionaryTypeParam implements Serializable {

    @ApiModelProperty(value = "应用编码", example = "应用编码")
    @NotBlank(message = "应用编码不能为空，请检查application参数")
    @Size(max = 32)
    private String application;

    @ApiModelProperty(value = "模块编号", example = "模块编号")
    private String module;

    @ApiModelProperty(value = "类型编码", example = "类型编码")
    private String code;

}
