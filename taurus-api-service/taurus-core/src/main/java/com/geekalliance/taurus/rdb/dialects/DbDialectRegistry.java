package com.geekalliance.taurus.rdb.dialects;

import com.baomidou.mybatisplus.annotation.DbType;
import com.geekalliance.taurus.rdb.dialects.impl.DbMySqlDialect;
import com.geekalliance.taurus.rdb.dialects.impl.DbOracleDialect;
import com.geekalliance.taurus.rdb.dialects.impl.DbSqlServerDialect;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * @author maxuqiang
 */
public class DbDialectRegistry {
    private final Map<DbType, DbDialect> DIALECT_ENUM_MAP = new EnumMap(DbType.class);

    public DbDialectRegistry() {
        this.DIALECT_ENUM_MAP.put(DbType.MYSQL, new DbMySqlDialect());
        this.DIALECT_ENUM_MAP.put(DbType.SQL_SERVER2005, new DbSqlServerDialect());
        this.DIALECT_ENUM_MAP.put(DbType.SQL_SERVER, new DbSqlServerDialect());
        this.DIALECT_ENUM_MAP.put(DbType.ORACLE, new DbOracleDialect());
    }

    public DbDialect getDialect(DbType dbType) {
        return this.DIALECT_ENUM_MAP.get(dbType);
    }

    public Collection<DbDialect> getDialects() {
        return Collections.unmodifiableCollection(this.DIALECT_ENUM_MAP.values());
    }
}
