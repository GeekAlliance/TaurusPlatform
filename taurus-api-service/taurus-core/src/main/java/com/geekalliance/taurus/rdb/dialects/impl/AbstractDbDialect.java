package com.geekalliance.taurus.rdb.dialects.impl;


import com.geekalliance.taurus.rdb.dialects.DbDialect;
import com.geekalliance.taurus.rdb.utils.MybatisSqlUtils;
import com.geekalliance.taurus.toolkit.StringPool;
import com.geekalliance.taurus.toolkit.utils.EscapeUtils;
import org.apache.ibatis.mapping.BoundSql;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author maxuqiang
 */
public abstract class AbstractDbDialect implements DbDialect {


    /**
     * 正则表达式 前瞻和追溯可以使字符串分割后继续保留分隔符
     */
    private final static String SPLIT_REGEX = "(?<=" + "( and )|( or )|( AND )|( OR )" + ")";

    @Override
    public String specialCharLike(String sql, Object parameterObject, BoundSql boundSql) {
        String[] conditions = MybatisSqlUtils.specialCharLike(sql, parameterObject, boundSql, this).split(SPLIT_REGEX);
        Pattern likeRegex = MybatisSqlUtils.getLikePattern();
        StringBuilder result = new StringBuilder();
        for (String condition : conditions) {
            StringBuilder temp = new StringBuilder(condition);
            Matcher likeConcatMatcher = likeRegex.matcher(condition.toLowerCase());
            if (likeConcatMatcher.find()) {
                String matcherStr = likeConcatMatcher.group();
                int index = condition.toLowerCase().indexOf(matcherStr);
                index = index + matcherStr.length();
                temp.insert(index, StringPool.BLANK_SPACE + EscapeUtils.ESCAPE_KEY + StringPool.BLANK_SPACE);
            }
            result.append(temp);
        }
        return result.toString();
    }

    @Override
    public Object sqlEscapeChar(String param) {
        return EscapeUtils.sqlEscapeChar(param);
    }
}
