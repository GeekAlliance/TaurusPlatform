package com.geekalliance.taurus.base.api.system.dto;

import com.geekalliance.taurus.core.validation.LengthConstraint;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * SaveDictionaryDTO
 *
 * @author geekeeper
 * @email geekkeeper@163.com
 * @date 2021-01-17 21:25
 */
@Data
public class AddDictionaryDTO implements Serializable {

    @ApiModelProperty(name = "字典类型")
    @NotBlank
    @Size(max = LengthConstraint.ID_LENGTH)
    private String typeId;

    @ApiModelProperty(name = "字典类型")
    @NotBlank
    @Size(max = LengthConstraint.CODE_LENGTH)
    private String typeCode;

    @ApiModelProperty(name = "字典编码")
    @NotBlank
    @Size(max = LengthConstraint.CODE_LENGTH)
    private Integer code;

    @ApiModelProperty(name = "字典值")
    @NotEmpty
    private String dictionaryValue;

    @ApiModelProperty(name = "备注信息")
    private String remark;

}
