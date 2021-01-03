package com.geekalliance.taurus.core.utils.converter;

import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * 默认转换器
 * String <-> String
 * <p>
 * 该转换器的设计为了解决某些字段中包含了 CSV 分隔符(逗号) 的问题
 * 2020-12-17 {@code CsvCore} 升级后，工具内部会处理逗号的问题，所以不再需要该转换器来处理逗号的问题
 * 所以，目前该类和 {@code AutoConverter} 完全一样
 *
 * @author lma
 * @date 2020/03/12
 */
public class AdvancedConverter implements Converter {

    /**
     * Java 对象的属性为 String，转为 CSV 中的内容也为字符串
     */
    @Override
    public String convertToCsvData(Object javaData) throws Exception {
        if (Objects.isNull(javaData)) {
            return "";
        }
        return String.valueOf(javaData);
    }


    /**
     * CSV 中的内容为字符串，转为 Java 对象的属性(String)
     */
    @Override
    public String convertToJavaData(String csvData) throws Exception {
        if (StringUtils.isEmpty(csvData)) {
            return null;
        }
        return csvData;
    }
}
