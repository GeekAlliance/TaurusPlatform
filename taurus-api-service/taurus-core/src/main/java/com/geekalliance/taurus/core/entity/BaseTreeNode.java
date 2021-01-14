package com.geekalliance.taurus.core.entity;

import lombok.Data;

import java.util.List;

/**
 * 通用树节点
 *
 * @author geekeeper
 * @version V1.0.0.0
 * @date 2021-01-11 22:26
 */
@Data
public class BaseTreeNode<T> {
    /**
     * 主键编号
     */
    private String id;
    /**
     * 节点编码
     */
    private String code;
    /**
     * 节点名称
     */
    private String name;
    /**
     * 父节点
     */
    private String parent;
    /**
     * 树层级
     */
    private Integer level;
    /**
     * 排序编号
     */
    private Integer sortNumber;
    /**
     * 节点数据
     */
    private T data;
    /**
     * 子节点
     */
    private List<BaseTreeNode> children;
}
