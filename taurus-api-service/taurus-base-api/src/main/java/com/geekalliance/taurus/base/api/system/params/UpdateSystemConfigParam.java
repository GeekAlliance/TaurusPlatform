package com.geekalliance.taurus.base.api.system.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * UpdateSystemConfigDTO
 * 更新配置信息模型
 *
 * @author geekeeper
 * @email geekkeeper@163.com
 * @date 2021-01-14 23:21
 */
@Data
public class UpdateSystemConfigParam implements Serializable {

    @ApiModelProperty(value = "主键编号", example = "主键编号")
    @NotBlank(message = "主键编号不能为空，请检查id参数")
    private String id;

    @ApiModelProperty(value = "配置名称", example = "配置名称")
    private String name;

    @ApiModelProperty(value = "配置值", example = "配置值")
    private String configValue;

    @ApiModelProperty(value = "备注信息", example = "备注信息")
    private String remark;
}
