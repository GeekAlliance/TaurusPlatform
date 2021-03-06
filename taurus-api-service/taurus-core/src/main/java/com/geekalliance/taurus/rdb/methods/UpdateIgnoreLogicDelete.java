package com.geekalliance.taurus.rdb.methods;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.geekalliance.taurus.rdb.enums.RdbSqlMethodEnum;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * @Description
 * @Date 2019/12/27
 * @Author maxuqiang
 **/

public class UpdateIgnoreLogicDelete extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        RdbSqlMethodEnum sqlMethod = RdbSqlMethodEnum.IGNORE_LOGIC_UPDATE_BY_ID;
        final String additional = optlockVersion(tableInfo);
        String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(),
                sqlSet(false, false, tableInfo, false, ENTITY, ENTITY_DOT),
                tableInfo.getKeyColumn(), ENTITY_DOT + tableInfo.getKeyProperty(), additional);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return addUpdateMappedStatement(mapperClass, modelClass, sqlMethod.getMethod(), sqlSource);
    }
}
