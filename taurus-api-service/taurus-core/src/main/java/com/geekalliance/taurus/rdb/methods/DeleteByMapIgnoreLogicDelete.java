package com.geekalliance.taurus.rdb.methods;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.geekalliance.taurus.rdb.enums.RdbSqlMethodEnum;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.Map;

/**
 * @author maxuqiang
 */
public class DeleteByMapIgnoreLogicDelete extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        RdbSqlMethodEnum sqlMethod = RdbSqlMethodEnum.IGNORE_LOGIC_DELETE_BY_MAP;
        String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), this.sqlWhereByMap(tableInfo));
        SqlSource sqlSource = this.languageDriver.createSqlSource(this.configuration, sql, Map.class);
        return this.addDeleteMappedStatement(mapperClass, sqlMethod.getMethod(), sqlSource);
    }
}
