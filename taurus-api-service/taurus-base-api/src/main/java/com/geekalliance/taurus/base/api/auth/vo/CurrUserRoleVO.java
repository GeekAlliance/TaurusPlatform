package com.geekalliance.taurus.base.api.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Date 2020/1/8
 * @Author maxuqiang
 **/
@ApiModel
@Data
public class CurrUserRoleVO implements Serializable {
    @ApiModelProperty(value = "角色编号", example = "角色编号")
    private String id;

    @ApiModelProperty(value = "角色名称", example = "角色名称")
    private String name;

    @ApiModelProperty(value = "角色类型", example = "角色类型")
    private String type;
}
