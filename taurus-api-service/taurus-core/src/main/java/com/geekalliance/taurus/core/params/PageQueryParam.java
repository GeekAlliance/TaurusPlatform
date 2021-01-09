package com.geekalliance.taurus.core.params;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geekalliance.taurus.toolkit.StringPool;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author maxuqiang
 */
@ApiModel(description = "分页查询")
@Data
public class PageQueryParam<T> implements Serializable {
    private static int DEFAULT_PAGE_NUM = 1;
    private static int DEFAULT_PAGE_SIZE = 10;

    @ApiModelProperty(value = "当前页", example = "1", required = true)
    private String pageNum;

    @ApiModelProperty(value = "每页显示数量", example = "10", required = true)
    private String pageSize;

    @ApiModelProperty(value = "查询参数", required = true)
    @Valid
    @NotNull(message = "查询参数对象不能为空")
    private T data;

    @JsonIgnore
    @ApiModelProperty(value = "排序参数", required = false)
    private List<OrderParam> orders;

    @JsonIgnore
    public int offset() {
        return this.getPageNum() > 0 ? (this.getPageNum() - 1) * this.getPageSize() : 0;
    }

    public int getPageNum() {
        if (!StringUtils.isNotBlank(pageNum)) {
            return DEFAULT_PAGE_NUM;
        }
        try {
            int pn = Integer.valueOf(pageNum);
            return pn <= 0 ? DEFAULT_PAGE_NUM : pn;
        } catch (Exception e) {
            return DEFAULT_PAGE_NUM;
        }
    }

    public int getPageSize() {
        if (!StringUtils.isNotBlank(pageSize)) {
            return DEFAULT_PAGE_SIZE;
        }
        try {
            int ps = Integer.valueOf(pageSize);
            return ps <= 0 ? DEFAULT_PAGE_SIZE : ps;
        } catch (Exception e) {
            return DEFAULT_PAGE_SIZE;
        }
    }

    @JsonIgnore
    public void setOrdersByColumn(String column) {
        if (!StringUtils.isNotBlank(column)) {
            return;
        }
        setDefaultOrders(StringPool.EMPTY, column);
    }

    @JsonIgnore
    public void setDefaultOrders(String alias, String column) {
        if (!StringUtils.isNotBlank(column)) {
            return;
        }
        List<OrderParam> orders = new ArrayList<>();
        if (!Objects.isNull(this.getOrders()) && !this.getOrders().isEmpty()) {
            orders = this.getOrders();
        }
        OrderParam orderParam = new OrderParam();
        orderParam.setAsc(false);
        if (StringUtils.isNotBlank(alias)) {
            orderParam.setColumn(alias + StringPool.DOT + column);
        } else {
            orderParam.setColumn(column);
        }
        orders.add(orderParam);
        this.setOrders(orders);
    }

    @JsonIgnore
    public PageQueryParam addOrder(OrderParam orderParam) {
        List<OrderParam> orders = new ArrayList<>();
        if (!Objects.isNull(this.getOrders()) && !this.getOrders().isEmpty()) {
            orders = this.getOrders();
        } else {
            this.setOrders(orders);
        }
        orders.add(orderParam);
        return this;
    }
}
