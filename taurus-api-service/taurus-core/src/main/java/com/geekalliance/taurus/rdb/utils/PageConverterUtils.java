package com.geekalliance.taurus.rdb.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.hollysys.platform.common.core.entity.Page;
import com.hollysys.platform.common.core.params.OrderParam;

import java.util.ArrayList;
import java.util.List;

public class PageConverterUtils {
    public static IPage getPage(Page page){
        com.baomidou.mybatisplus.extension.plugins.pagination.Page p = new com.baomidou.mybatisplus.extension.plugins.pagination.Page(page.getCurrent(),page.getSize(),page.isSearchCount());
        List<OrderItem> orders = new ArrayList<>();
        if(null != page.getOrders() && page.getOrders().size()>0){
            List<OrderParam> orderParams = page.getOrders();
            for(OrderParam orderParam:orderParams){
                OrderItem orderItem = new OrderItem();
                orderItem.setAsc(orderParam.getAsc());
                orderItem.setColumn(orderParam.getColumn());
                orders.add(orderItem);
            }
        }
        p.setOrders(orders);
        return p;
    }

    public static Page getPage(IPage page){
        Page p = new Page();
        p.setTotal(page.getTotal());
        p.setRecords(page.getRecords());
        p.setSize(page.getSize());
        p.setCurrent(page.getCurrent());
        return p;
    }
}
