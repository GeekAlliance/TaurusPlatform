package com.geekalliance.taurus.rdb.dialects;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.ClassUtils;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.dialects.IDialect;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author maxuqiang
 */
public class DbDialectFactory {
    private static final DbDialectRegistry DIALECT_REGISTRY = new DbDialectRegistry();
    private static final Map<String, DbDialect> DIALECT_CACHE = new ConcurrentHashMap();

    public static DbDialect getDialect(DbType dbType) {
        return DIALECT_REGISTRY.getDialect(dbType);
    }

    public static DbDialect getDialect(String dialectClazz) {
        return DIALECT_CACHE.computeIfAbsent(dialectClazz, DbDialectFactory::classToDialect);
    }

    private static DbDialect classToDialect(String dialectClazz) {
        DbDialect dialect = null;

        try {
            Class<?> clazz = Class.forName(dialectClazz);
            if (IDialect.class.isAssignableFrom(clazz)) {
                dialect = newInstance((Class<? extends DbDialect>) clazz);
            }
            return dialect;
        } catch (ClassNotFoundException var3) {
            throw ExceptionUtils.mpe("Class : %s is not found", dialectClazz);
        }
    }

    private static DbDialect newInstance(Class<? extends DbDialect> dialectClazz) {
        DbDialect dialect = ClassUtils.newInstance(dialectClazz);
        Assert.notNull(dialect, "the value of the dialect property in mybatis configuration.xml is not defined.");
        return dialect;
    }
}
