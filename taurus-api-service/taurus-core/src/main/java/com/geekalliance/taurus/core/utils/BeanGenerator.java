package com.geekalliance.taurus.core.utils;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;


/**
 * 各层bean之间的深度转换，vo/domain , dto , po
 * @author maxuqiang
 */
public interface BeanGenerator {

    /**
     * 单个对象的深度复制及类型转换，
     *
     * @param s   数据对象
     * @param clz 复制目标类型
     */
    <T extends Serializable, S extends Serializable> T convert(S s, Class<T> clz);

    /**
     * 容器深度复制
     */
    <T extends Serializable, S extends Serializable> Collection<T> convert(Collection<S> s,
                                                                           Class<T> clz);

    /**
     * list深度复制
     */
    <T extends Serializable, S extends Serializable> List<T> convert(List<S> s, Class<T> clz);

    /**
     * set深度复制
     */
    <T extends Serializable, S extends Serializable> Set<T> convert(Set<S> s, Class<T> clz);

    /**
     * 数组深度复制
     */
    <T extends Serializable, S extends Serializable> T[] convert(S[] s, Class<T> clz);

}