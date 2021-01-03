package com.geekalliance.taurus.rdb.interceptor;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.toolkit.JdbcUtils;
import com.geekalliance.taurus.core.utils.BeanGenerator;
import com.geekalliance.taurus.rdb.dialects.DbDialect;
import com.geekalliance.taurus.rdb.dialects.DbDialectFactory;
import com.geekalliance.taurus.rdb.service.DataSourceService;
import com.geekalliance.taurus.rdb.utils.MybatisSqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Properties;

/**
 * @author maxuqiang
 */
@Slf4j
@Component
@Intercepts(@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
        RowBounds.class, ResultHandler.class}))
public class SpecialCharLikeInterceptor implements Interceptor {
    private DbType dbType;
    private DbDialect dialect;

    @Autowired
    protected DataSourceService dataSourceService;

    @Autowired
    protected BeanGenerator beanGenerator;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        DbType dbType = (DbType) Optional.ofNullable(this.dbType).orElse(JdbcUtils.getDbType(dataSourceService.getCurrentDbUrl()));
        DbDialect dialect = (DbDialect) Optional.ofNullable(this.dialect).orElse(DbDialectFactory.getDialect(dbType));
        // 拦截sql
        Object[] args = invocation.getArgs();
        MappedStatement statement = (MappedStatement) args[0];
        Object parameterObject = args[1];
        BoundSql boundSql = statement.getBoundSql(parameterObject);
        String sql = boundSql.getSql();
        // 处理特殊字符
        String sqlResult = dialect.specialCharLike(sql, parameterObject, boundSql);
        MybatisSqlUtils.resetSql2Invocation(invocation, sqlResult);
        // 返回
        return invocation.proceed();
    }

    @Override
    public void setProperties(Properties prop) {
        String dialectType = prop.getProperty("dialectType");
        String dialectClazz = prop.getProperty("dialectClazz");
        if (StringUtils.isNotBlank(dialectType)) {
            this.setDbType(DbType.getDbType(dialectType));
        }
        if (StringUtils.isNotBlank(dialectClazz)) {
            this.setDialect(DbDialectFactory.getDialect(dialectClazz));
        }
    }

    public SpecialCharLikeInterceptor setDbType(final DbType dbType) {
        this.dbType = dbType;
        return this;
    }

    public SpecialCharLikeInterceptor setDialect(final DbDialect dialect) {
        this.dialect = dialect;
        return this;
    }
}
