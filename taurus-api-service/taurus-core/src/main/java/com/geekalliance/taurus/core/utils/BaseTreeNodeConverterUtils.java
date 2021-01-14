package com.geekalliance.taurus.core.utils;

import com.geekalliance.taurus.core.entity.BaseTreeNode;
import com.geekalliance.taurus.core.enums.TreeEnum;
import com.geekalliance.taurus.toolkit.utils.CollectionUtils;
import com.geekalliance.taurus.toolkit.utils.ReflectUtils;
import com.geekalliance.taurus.toolkit.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class BaseTreeNodeConverterUtils {
    public static <T> List<BaseTreeNode<T>> converter(List<T> sources) {
        List<BaseTreeNode<T>> baseTreeNodes = converterSourceToBaseTreeNode(sources);
        List<BaseTreeNode<T>> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(baseTreeNodes)) {
            for (BaseTreeNode<T> baseTreeNode : baseTreeNodes) {
                if (TreeEnum.ROOT_NODE.getCode().equals(baseTreeNode.getParent())) {
                    result.add(baseTreeNode);
                    fillChildrenByParentNode(baseTreeNode, baseTreeNodes);
                }
            }
        }
        return result;
    }

    private static <T> void fillChildrenByParentNode(BaseTreeNode<T> baseTreeNode, List<BaseTreeNode<T>> baseTreeNodes) {
        for (BaseTreeNode<T> baseTreeNodeTemp : baseTreeNodes) {
            if(StringUtils.isNotBlank(baseTreeNodeTemp.getParent()) && StringUtils.isNotBlank(baseTreeNode.getId())){
                if (baseTreeNodeTemp.getParent().equals(baseTreeNode.getId())) {
                    if(Objects.isNull(baseTreeNode.getChildren())){
                        baseTreeNode.setChildren(new ArrayList<>());
                    }
                    baseTreeNode.getChildren().add(baseTreeNodeTemp);
                    fillChildrenByParentNode(baseTreeNodeTemp, baseTreeNodes);
                }
            }
        }
    }

    private static <T> List<BaseTreeNode<T>> converterSourceToBaseTreeNode(List<T> sources) {
        List<BaseTreeNode<T>> baseTreeNodes = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(sources)) {
            for (T source : sources) {
                baseTreeNodes.add(converterSourceToBaseTreeNode(source));
            }
        }
        return baseTreeNodes;
    }

    private static <T> BaseTreeNode<T> converterSourceToBaseTreeNode(T source) {
        BaseTreeNode<T> baseTreeNode = new BaseTreeNode();
        baseTreeNode.setData(source);
        List<Field> baseTreeNodeFields = ReflectUtils.getDeclaredFields(BaseTreeNode.class);
        List<Field> sourceFields = ReflectUtils.getDeclaredFields(source.getClass());
        if (CollectionUtils.isNotEmpty(sourceFields)) {
            for (Field sourceField : sourceFields) {
                sourceField.setAccessible(true);
                for (Field baseTreeNodeField : baseTreeNodeFields) {
                    baseTreeNodeField.setAccessible(true);
                    if (baseTreeNodeField.getName().equals(sourceField.getName())) {
                        try {
                            baseTreeNodeField.set(baseTreeNode, sourceField.get(source));
                        } catch (IllegalAccessException e) {
                            log.error("converter source to base tree node error illegal access exception");
                        }
                    }
                }
            }
        }
        return baseTreeNode;
    }
}
