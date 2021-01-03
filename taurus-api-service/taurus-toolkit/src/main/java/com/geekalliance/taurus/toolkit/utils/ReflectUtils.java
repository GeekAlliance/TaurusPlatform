package com.geekalliance.taurus.toolkit.utils;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class ReflectUtils {
    public static List<Field> getDeclaredFields(Class clazz) {
        List<Field> fieldList = new ArrayList<>();
        Class tempClass = clazz;
        while (tempClass != null) {
            //当父类为null的时候说明到达了最上层的父类(Object类)
            fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            //得到父类,然后赋给自己
            tempClass = tempClass.getSuperclass();
        }
        return fieldList;
    }

    public static Field getFieldByName(List<Field> fields, String name) throws NoSuchFieldException {
        List<Field> fieldsFilter = new ArrayList<>();
        if (!Objects.isNull(fields) && !fields.isEmpty() && StringUtils.isNotBlank(name)) {
            fieldsFilter = fields.stream().filter((Field field) -> field.getName().equals(name)).collect(Collectors.toList());
        }
        if (!Objects.isNull(fieldsFilter) && !fieldsFilter.isEmpty()) {
            return fieldsFilter.get(0);
        } else {
            throw new NoSuchFieldException("field " + name + "not found");
        }
    }

    public static boolean isBaseType(Object object) {
        Class className = object.getClass();
        if (className.equals(java.lang.Integer.class) ||
                className.equals(java.lang.String.class) ||
                className.equals(java.lang.Byte.class) ||
                className.equals(java.lang.Long.class) ||
                className.equals(java.lang.Double.class) ||
                className.equals(java.lang.Float.class) ||
                className.equals(java.lang.Character.class) ||
                className.equals(java.lang.Short.class) ||
                className.equals(java.lang.Boolean.class)) {
            return true;
        }
        return false;
    }
}
