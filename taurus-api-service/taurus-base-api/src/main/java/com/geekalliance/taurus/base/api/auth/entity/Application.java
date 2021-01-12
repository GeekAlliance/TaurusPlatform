package com.geekalliance.taurus.base.api.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.geekalliance.taurus.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 应用表
 */
@ApiModel(value = "应用表")
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("base_application")
public class Application extends BaseEntity {
    /**
     * 应用编码
     */
    @ApiModelProperty(value = "应用编码")
    private String code;

    /**
     * 应用名称
     */
    @ApiModelProperty(value = "应用名称")
    private String name;

    /**
     * 链接地址
     */
    @ApiModelProperty(value = "链接地址")
    private String link;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Byte statusNumber;

    /**
     * 备注信息
     */
    @ApiModelProperty(value = "备注信息")
    private String remark;

    /**
     * 启用标识
     */
    @ApiModelProperty(value = "启用标识")
    private String enableFlag;

    /**
     * 删除标识
     */
    @ApiModelProperty(value = "删除标识")
    private String deleteFlag;
}