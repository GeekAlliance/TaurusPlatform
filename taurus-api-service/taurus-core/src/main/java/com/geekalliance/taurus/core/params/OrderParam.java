package com.geekalliance.taurus.core.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Date 2019/12/24
 * @Author maxuqiang
 **/
@ApiModel(description = "排序参数")
@Data
public class OrderParam implements Serializable {
    public OrderParam() {
    }

    public OrderParam(String column, Boolean asc) {
        this.column = column;
        this.asc = asc;
    }

    @ApiModelProperty(value = "数据库排序列", example = "create_time")
    private String column;

    @ApiModelProperty(value = "是否正序排序", example = "true")
    private Boolean asc = true;
}
