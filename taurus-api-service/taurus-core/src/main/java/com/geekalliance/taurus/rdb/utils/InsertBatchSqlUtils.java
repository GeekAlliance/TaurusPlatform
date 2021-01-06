package com.geekalliance.taurus.rdb.utils;


import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.geekalliance.taurus.rdb.enums.RdbSqlMethodEnum;
import com.geekalliance.taurus.toolkit.StringPool;
import com.geekalliance.taurus.toolkit.utils.DateUtils;
import com.geekalliance.taurus.toolkit.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

/**
 * @author maxuqiang
 */
@Slf4j
public class InsertBatchSqlUtils {

    public static String genInsertSql(Class<?> modelClass, Object obj) {
        if (Objects.nonNull(obj)) {
            TableInfo tableInfo = TableInfoHelper.getTableInfo(modelClass);
            String insertSqlColumn = tableInfo.getKeyInsertSqlColumn(false) +
                    filterTableFieldInfo(tableInfo.getFieldList(), null, TableFieldInfo::getInsertSqlColumn, StringPool.EMPTY);
            String columnScript = StringPool.LEFT_BRACKET + insertSqlColumn.substring(0, insertSqlColumn.length() - 1) + StringPool.RIGHT_BRACKET;
            StringBuilder valuesScript = new StringBuilder();
            valuesScript.append(valueScript(tableInfo, obj));
            String sql = String.format(RdbSqlMethodEnum.INSERT_BATCH.getSql(), tableInfo.getTableName(), columnScript, valuesScript);
            return sql;
        }
        return StringPool.EMPTY;
    }

    public static String genInsertBatchSql(Class<?> modelClass, Collection<?> list) {
        if (!CollectionUtils.isEmpty(list)) {
            TableInfo tableInfo = TableInfoHelper.getTableInfo(modelClass);
            String insertSqlColumn = tableInfo.getKeyInsertSqlColumn(false) +
                    filterTableFieldInfo(tableInfo.getFieldList(), null, TableFieldInfo::getInsertSqlColumn, StringPool.EMPTY);
            String columnScript = StringPool.LEFT_BRACKET + insertSqlColumn.substring(0, insertSqlColumn.length() - 1) + StringPool.RIGHT_BRACKET;
            StringBuilder valuesScript = new StringBuilder();
            list.forEach(data -> {
                if (valuesScript.length() > 0) {
                    valuesScript.append(StringPool.COMMA);
                }
                valuesScript.append(valueScript(tableInfo, data));
            });
            String sql = String.format(RdbSqlMethodEnum.INSERT_BATCH.getSql(), tableInfo.getTableName(), columnScript, valuesScript);
            return sql;
        }

        return StringPool.EMPTY;
    }

    /**
     * 过滤 TableFieldInfo 集合, join 成字符串
     */
    private static String filterTableFieldInfo(List<TableFieldInfo> fieldList, Predicate<TableFieldInfo> predicate,
                                               Function<TableFieldInfo, String> function, String joiningVal) {
        Stream<TableFieldInfo> infoStream = fieldList.stream();
        if (predicate != null) {
            return infoStream.filter(predicate).map(function).collect(joining(joiningVal));
        }
        return infoStream.map(function).collect(joining(joiningVal));
    }

    private static String valueScript(TableInfo tableInfo, Object data) {
        String valueScript = getValue(data, tableInfo.getKeyProperty()) + StringPool.COMMA +
                filterTableFieldInfo(tableInfo.getFieldList(), null, i -> getValue(data, i.getProperty()), StringPool.COMMA);
        valueScript = StringPool.LEFT_BRACKET + valueScript.substring(0, valueScript.length()) + StringPool.RIGHT_BRACKET;
        return valueScript;
    }

    private static String getValue(Object data, String fieldName) {
        try {
            Field field = data.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            if (Objects.nonNull(field.get(data))) {
                if (field.getType().equals(Date.class)) {
                    return StringPool.SINGLE_QUOTE + DateUtils.parseDateToString(((Date) field.get(data)), DateUtils.DATE_FORMAT_YYYY_MM_DD_HH_SS) + StringPool.SINGLE_QUOTE;
                } else if (isNumber(field)) {
                    return String.valueOf(field.get(data));
                } else {
                    return StringPool.SINGLE_QUOTE + String.valueOf(field.get(data)) + StringPool.SINGLE_QUOTE;
                }
            }
        } catch (NoSuchFieldException e) {
            log.error("get value no such field exception error fieldName {} data {}", fieldName, JsonUtils.deserializer(data));
        } catch (IllegalAccessException e) {
            log.error("get value illegal access exception error fieldName {} data {}", fieldName, JsonUtils.deserializer(data));
        }
        return StringPool.NULL;
    }

    private static boolean isNumber(Field field) {
        if (field.getType().equals(int.class) || field.getType().equals(Integer.class)) {
            return true;
        }
        if (field.getType().equals(double.class) || field.getType().equals(Double.class)) {
            return true;
        }
        if (field.getType().equals(long.class) || field.getType().equals(Long.class)) {
            return true;
        }
        if (field.getType().equals(short.class) || field.getType().equals(short.class)) {
            return true;
        }
        if (field.getType().equals(float.class) || field.getType().equals(Float.class)) {
            return true;
        }
        return false;
    }
}
