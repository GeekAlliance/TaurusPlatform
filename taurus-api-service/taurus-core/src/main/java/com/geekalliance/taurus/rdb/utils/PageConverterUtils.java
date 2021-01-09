package com.geekalliance.taurus.rdb.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.geekalliance.taurus.core.params.OrderParam;
import com.geekalliance.taurus.core.params.PageQueryParam;
import com.geekalliance.taurus.toolkit.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class PageConverterUtils {
    public static IPage getPage(PageQueryParam<?> param) {
        return getPage(param, true);
    }

    public static IPage getPage(PageQueryParam<?> param, boolean isSearchCount) {
        Page p = new Page(param.getPageNum(), param.getPageSize(), isSearchCount);
        List<OrderItem> orders = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(param.getOrders())) {
            List<OrderParam> orderParams = param.getOrders();
            for (OrderParam orderParam : orderParams) {
                OrderItem orderItem = new OrderItem();
                orderItem.setAsc(orderParam.getAsc());
                orderItem.setColumn(orderParam.getColumn());
                orders.add(orderItem);
            }
        }
        p.setOrders(orders);
        return p;
    }

}
