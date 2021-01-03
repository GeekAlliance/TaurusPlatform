package com.geekalliance.taurus.toolkit.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * @author maxuqiang
 */
public class XmlUtils {
    public static Object xmlStrToObject(Class<?> clazz, String xmlStr) throws JAXBException, IOException {
        Object xmlObject = null;
        Reader reader = null;
        //利用JAXBContext将类转为一个实例
        JAXBContext context = JAXBContext.newInstance(clazz);
        //XMl 转为对象的接口
        Unmarshaller unmarshaller = context.createUnmarshaller();
        reader = new StringReader(xmlStr);
        xmlObject = unmarshaller.unmarshal(reader);
        if (reader != null) {
            reader.close();
        }
        return xmlObject;
    }

    public static String objectToXmlStr(Object obj) throws JAXBException, IOException {
        // 创建输出流
        StringWriter sw = new StringWriter();
        // 利用jdk中自带的转换类实现
        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
        // 格式化xml输出的格式
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        // 将对象转换成输出流形式的xml
        marshaller.marshal(obj, sw);
        return sw.toString();
    }

}
