package com.geekalliance.taurus.rdb.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.geekalliance.taurus.rdb.dialects.DbDialect;
import com.geekalliance.taurus.toolkit.StringPool;
import com.geekalliance.taurus.toolkit.utils.EscapeUtils;
import com.geekalliance.taurus.toolkit.utils.ReflectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author maxuqiang
 */
@Slf4j
public class MybatisSqlUtils {
    private static final StringBuilder LIKE_REGEX = new StringBuilder();

    /**
     * 匹配 like concat(*)字符
     */
    private static final String LIKE_CONCAT_REGEX = "([\\s]+like[\\s]+concat\\([\\s\\S]*?\\))";

    /**
     * 匹配 like concat(*)字符 或者 like ？
     */
    private static final String LIKE_CONCAT_REGEX_LIKE = "([\\s]+like[\\s]+concat\\([\\s\\S]*?\\?)|([\\s]+like[\\s]*?\\?)";

    private static final String LIKE_BLANK_SPACE_REGEX = "([\\s]+like[\\s]+?)";

    private static final String LIKE_MARK_REGEX = "([\\s]+like[\\s]+\\?)";

    private static final String LIKE_PERCENT_REGEX = "([\\s]+like[\\s]+\\%[\\s\\S]*\\%)";

    private static Pattern LIKE_PATTERN = null;

    private static Pattern LIKE_BLANK_SPACE_PATTERN = null;

    private static Pattern LIKE_MARK_REGEX_PATTERN = null;

    static {
        LIKE_REGEX.append(LIKE_CONCAT_REGEX);
        LIKE_REGEX.append(StringPool.PIPE);
        LIKE_REGEX.append(LIKE_MARK_REGEX);
        LIKE_REGEX.append(StringPool.PIPE);
        LIKE_REGEX.append(LIKE_PERCENT_REGEX);
        LIKE_PATTERN = Pattern.compile(LIKE_REGEX.toString());
        LIKE_BLANK_SPACE_PATTERN = Pattern.compile(LIKE_CONCAT_REGEX_LIKE);
        LIKE_MARK_REGEX_PATTERN = Pattern.compile(LIKE_MARK_REGEX);
    }

    public static boolean isLikePatternMatcher(String sql) {
        Matcher likeMatcher = LIKE_PATTERN.matcher(sql);
        return likeMatcher.find();
    }

    public static boolean isLikeBlankSpacePatternMatcher(String sql) {
        Matcher likeMatcher = LIKE_BLANK_SPACE_PATTERN.matcher(sql);
        return likeMatcher.find();
    }

    public static boolean isLikeMarkPatternMatcher(String sql) {
        Matcher likeMatcher = LIKE_MARK_REGEX_PATTERN.matcher(sql);
        return likeMatcher.find();
    }

    public static Pattern getLikePattern() {
        return LIKE_PATTERN;
    }

    /**
     * 获取 mybatis执行sql
     *
     * @param invocation
     * @return
     */
    public static String getSqlByInvocation(Invocation invocation) {
        final Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameterObject = args[1];
        BoundSql boundSql = ms.getBoundSql(parameterObject);
        return boundSql.getSql();
    }

    /**
     * 给 mybatis设置修改后的sql
     *
     * @param invocation
     * @param sql
     * @throws SQLException
     */
    public static void resetSql2Invocation(Invocation invocation, String sql) throws SQLException {
        final Object[] args = invocation.getArgs();
        MappedStatement statement = (MappedStatement) args[0];
        Object parameterObject = args[1];
        BoundSql boundSql = statement.getBoundSql(parameterObject);
        MappedStatement newStatement = newMappedStatement(statement, new BoundSqlSqlSource(boundSql));
        MetaObject msObject = MetaObject.forObject(newStatement, new DefaultObjectFactory(), new DefaultObjectWrapperFactory(), new DefaultReflectorFactory());
        msObject.setValue("sqlSource.boundSql.sql", sql);
        args[0] = newStatement;
    }

    /**
     * 给 mybatis设置修改后的sql 参数
     *
     * @param ms
     * @param newSqlSource
     * @return
     */
    private static MappedStatement newMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder =
                new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
            StringBuilder keyProperties = new StringBuilder();
            for (String keyProperty : ms.getKeyProperties()) {
                keyProperties.append(keyProperty).append(",");
            }
            keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
            builder.keyProperty(keyProperties.toString());
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        return builder.build();
    }

    /**
     * 根据正则判断sql中是否有模糊匹配，处理参数个数
     *
     * @param sql
     * @param parameterObject
     * @param boundSql
     * @param dialect
     * @return
     */
    public static String specialCharLike(String sql, Object parameterObject, BoundSql boundSql, DbDialect dialect) {
        String sqlTemp = sql.toLowerCase();
        if (!isLikePatternMatcher(sqlTemp)) {
            return sql;
        }
        // 获取关键字的个数（去重）
        String[] strList = sqlTemp.split("(?<=" + StringPool.BACK_SLASH + StringPool.QUESTION_MARK + ")");
        Set<String> keyNames = new HashSet<>();
        for (int i = 0; i < strList.length; i++) {
            if (isLikeBlankSpacePatternMatcher(strList[i])) {
                String keyName = boundSql.getParameterMappings().get(i).getProperty();
                keyNames.add(keyName);
            }
        }
        // 对关键字进行特殊字符“清洗”，如果有特殊字符的，在特殊字符前添加转义字符
        HashMap parameter = (HashMap) parameterObject;
        for (String keyName : keyNames) {
            // 第二种情况：未使用条件构造器，但是在service层进行了查询关键字与模糊查询符`%`手动拼接
            if (keyName.contains("ew.paramNameValuePairs.") && isLikeMarkPatternMatcher(sqlTemp)) {
                // 在业务层进行条件构造产生的模糊查询关键字
                QueryWrapper wrapper = (QueryWrapper) parameter.get("ew");
                HashMap valuePairs = (HashMap) wrapper.getParamNameValuePairs();
                String[] keyList = keyName.split(StringPool.BACK_SLASH + StringPool.DOT);
                Object a = valuePairs.get(keyList[2]);
                if (a instanceof String && isEscapeChar(a)) {
                    valuePairs.put(keyList[2],
                            StringPool.PERCENT + dialect.sqlEscapeChar(a.toString().substring(1, a.toString().length() - 1)) + StringPool.PERCENT);
                }
            } else MybatisSqlUtils.escapeChar(parameter, keyName, !keyName.contains("ew.paramNameValuePairs.") && isLikeMarkPatternMatcher(sqlTemp), dialect, 0);
        }
        return sql;
    }

    /**
     * 替换需要sql中需要转意的特殊字符
     *
     * @param parameter
     * @param keyName
     * @param isServiceAppendPercent
     * @param dialect
     * @param index
     */
    private static void escapeChar(Object parameter, String keyName, boolean isServiceAppendPercent, DbDialect dialect, int index) {
        if (StringUtils.isNotBlank(keyName)) {
            String[] keyList = keyName.split(StringPool.BACK_SLASH + StringPool.DOT);
            if (index >= keyList.length) {
                return;
            }
            if (parameter instanceof HashMap) {
                HashMap param = (HashMap) parameter;
                Object value = param.get(keyList[index]);
                if (isEscapeChar(value)) {
                    if (isServiceAppendPercent) {
                        param.put(keyName, StringPool.PERCENT + dialect.sqlEscapeChar(value.toString().substring(1, value.toString().length() - 1)) + StringPool.PERCENT);
                    } else {
                        param.put(keyName, dialect.sqlEscapeChar(value.toString()));
                    }
                } else if (!ReflectUtils.isBaseType(value)) {
                    try {
                        param.put(keyList[index], BeanUtils.cloneBean(value));
                        escapeChar(param.get(keyList[index]), keyName, isServiceAppendPercent, dialect, ++index);
                    } catch (Exception e) {
                        log.error("special char like interceptor clone bean error");
                    }
                }
            } else if (!isEscapeChar(parameter)) {
                Class clazz = parameter.getClass();
                List<Field> fields = ReflectUtils.getDeclaredFields(clazz);
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (field.getName().equals(keyList[index])) {
                        try {
                            if (isEscapeChar(field.get(parameter))) {
                                String value = field.get(parameter).toString();
                                if (isServiceAppendPercent) {
                                    field.set(parameter, StringPool.PERCENT + dialect.sqlEscapeChar(value.substring(1, value.length() - 1)) + StringPool.PERCENT);
                                } else {
                                    field.set(parameter, dialect.sqlEscapeChar(value));
                                }
                            } else if (!ReflectUtils.isBaseType(field.get(parameter))) {
                                escapeChar(field.get(parameter), keyName, isServiceAppendPercent, dialect, ++index);
                            }
                        } catch (IllegalAccessException e) {
                            log.error("special char like interceptor set object value error");
                        }
                    }
                }
            }
        }
    }

    /**
     * 判断是否需要处理特殊字符
     */
    private static Boolean isEscapeChar(Object value) {
        return value instanceof String && EscapeUtils.isEscapeChar(value.toString());
    }
}
