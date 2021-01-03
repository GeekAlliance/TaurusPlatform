package com.geekalliance.taurus.rdb.dialects;

import org.apache.ibatis.mapping.BoundSql;

/**
 * @author maxuqiang
 */
public interface DbDialect {

    /**
     * 处理Like模糊查询特殊字符
     * @param sql 原始sql
     * @param parameterObject mybatis参数对象
     * @param boundSql mybatis boundSql
     * @return 处理后的sql
     */
    String specialCharLike(String sql, Object parameterObject, BoundSql boundSql);

    /**
     * 处理Like模糊查询特殊字符
     * @param param 需要处理的内容
     * @return 处理后的值
     */
    Object sqlEscapeChar(String param);
}
