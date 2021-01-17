package com.geekalliance.taurus.core.utils.converter.creator;

/**
 * @author maxuqiang
 */
public interface Creator<T> {
    /**
     * 转换类创建对象接口
     * @param source 转换类创建对象
     * @return 返回目标对象
     */
    T create(Object source);
}
