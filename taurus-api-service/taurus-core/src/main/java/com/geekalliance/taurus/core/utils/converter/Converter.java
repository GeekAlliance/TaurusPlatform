package com.geekalliance.taurus.core.utils.converter;

/**
 * 转换器
 *
 * @author lma
 * @date 2020/03/12
 */
public interface Converter<T> {

    /**
     * 将 Java 对象中的属性转换为 CSV 文件中的内容
     * 在 CSV 中，所有的内容都展示为字符串
     */
    String convertToCsvData(T javaData) throws Exception;

    /**
     * 将 CSV 文件中的内容(字符串)，转换为 Java 对象中的属性
     */
    T convertToJavaData(String csvData) throws Exception;

}
