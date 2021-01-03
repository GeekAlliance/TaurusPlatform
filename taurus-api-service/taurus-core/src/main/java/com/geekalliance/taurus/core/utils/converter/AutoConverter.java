package com.geekalliance.taurus.core.utils.converter;

import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * 默认转换器
 * String <-> String
 *
 * @author lma
 * @date 2020/03/12
 */
public class AutoConverter implements Converter {

    /**
     * Java 对象的属性为 String，转为 CSV 中的内容也为字符串
     */
    @Override
    public String convertToCsvData(Object javaData) throws Exception {
        if(Objects.isNull(javaData)){
            return "";
        }
        return String.valueOf(javaData);
    }


    /**
     * CSV 中的内容为字符串，转为 Java 对象的属性(String)
     */
    @Override
    public String convertToJavaData(String csvData) throws Exception {
        if(StringUtils.isEmpty(csvData)){
            return null;
        }
        return csvData;
    }
}
