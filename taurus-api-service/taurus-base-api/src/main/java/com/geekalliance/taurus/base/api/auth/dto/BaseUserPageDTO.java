package com.geekalliance.taurus.base.api.auth.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Date 2019/12/18
 * @Author maxuqiang
 **/
@ApiModel
@Data
public class BaseUserPageDTO implements Serializable {
    @ApiModelProperty(value = "用户编号", example = "用户编号")
    private String id;

    @ApiModelProperty(value = "用户名", example = "账号")
    private String username;

    @ApiModelProperty(value = "姓名", example = "姓名")
    private String personName;

    @ApiModelProperty(value = "昵称", example = "昵称")
    private String nickName;

    @ApiModelProperty(value = "用户类型", example = "用户类型")
    private String typeName;

}
