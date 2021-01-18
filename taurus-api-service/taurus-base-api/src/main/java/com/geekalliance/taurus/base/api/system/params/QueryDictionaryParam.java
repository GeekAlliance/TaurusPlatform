package com.geekalliance.taurus.base.api.system.params;

import com.geekalliance.taurus.base.api.system.validation.DictionaryValidationGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * DictionaryQueryDTO
 *
 * @author geekeeper
 * @email geekkeeper@163.com
 * @date 2021-01-17 21:07
 */
@Data
public class QueryDictionaryParam extends DictionaryValidationGroup {

    @ApiModelProperty(value = "类型编号", example = "类型编号")
    private String typeId;

    @ApiModelProperty(value = "类型编码", example = "类型编码")
    private String typeCode;
}
