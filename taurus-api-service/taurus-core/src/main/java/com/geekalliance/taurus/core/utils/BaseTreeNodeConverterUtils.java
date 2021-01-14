package com.geekalliance.taurus.core.utils;

import com.geekalliance.taurus.core.entity.BaseTreeNode;

import java.util.ArrayList;
import java.util.List;

public class BaseTreeNodeConverterUtils {
    public static <T> List<BaseTreeNode<T>> converter(List<T> source) {
        List<BaseTreeNode<T>> result = new ArrayList<>();
        return result;
    }
}
