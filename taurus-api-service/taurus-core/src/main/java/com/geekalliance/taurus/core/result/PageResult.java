package com.geekalliance.taurus.core.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Date 2019/12/17
 * @Author maxuqiang
 **/
@ApiModel
@Data
public class PageResult<T>{
    @ApiModelProperty(value = "总页数", example = "总页数")
    private String total;

    @ApiModelProperty(value = "每页数量", example = "每页数量")
    private String size;

    @ApiModelProperty(value = "当前页", example = "当前页")
    private String current;

    @ApiModelProperty(value = "列表数据")
    private List<T> list;
}
