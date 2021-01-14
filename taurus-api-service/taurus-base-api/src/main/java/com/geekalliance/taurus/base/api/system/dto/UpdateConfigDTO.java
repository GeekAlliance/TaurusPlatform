package com.geekalliance.taurus.base.api.system.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * UpdateSystemConfigDTO
 * 更新配置信息模型
 *
 * @author geekeeper
 * @email geekkeeper@163.com
 * @date 2021-01-14 23:21
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateConfigDTO extends AddConfigDTO {

    @ApiModelProperty(value = "主键编号", example = "主键编号")
    @NotBlank(message = "主键编号不能为空，请检查id参数")
    private String id;

    @ApiModelProperty(value = "配置编码", example = "配置编码")
    @NotBlank(message = "配置编码不能为空，请检查code参数")
    private String code;


}
