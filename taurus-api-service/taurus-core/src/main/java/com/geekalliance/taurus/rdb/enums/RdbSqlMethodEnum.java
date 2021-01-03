package com.geekalliance.taurus.rdb.enums;

/**
 * @Description
 * @Date 2019/12/27
 * @Author maxuqiang
 **/

public enum RdbSqlMethodEnum {
    IGNORE_LOGIC_SELECT_BY_ID("getByIdIgnoreLogicDelete", "根据ID 查询一条数据", "SELECT %s FROM %s WHERE %s=#{%s} %s"),
    IGNORE_LOGIC_UPDATE_BY_ID("updateByIdIgnoreLogicDelete", "根据ID 选择修改数据", "<script>\nUPDATE %s %s WHERE %s=#{%s} %s\n</script>"),
    IGNORE_LOGIC_DELETE_BY_ID("deleteByIdIgnoreLogicDelete", "根据ID 删除一条数据", "<script>\nDELETE FROM %s WHERE %s=#{%s}\n</script>"),
    IGNORE_LOGIC_DELETE_BY_MAP("deleteByMapIgnoreLogicDelete", "根据columnMap 条件删除记录", "<script>\nDELETE FROM %s %s\n</script>"),
    IGNORE_LOGIC_DELETE("deleteIgnoreLogicDelete", "根据 entity 条件删除记录", "<script>\nDELETE FROM %s %s %s\n</script>"),
    IGNORE_LOGIC_DELETE_BATCH_BY_IDS("deleteBatchIdsIgnoreLogicDelete", "根据ID集合，批量删除数据", "<script>\nDELETE FROM %s WHERE %s IN (%s)\n</script>"),
    INSERT_BATCH("insertBatch", "插入一条数据（选择字段插入）", "\nINSERT INTO %s %s VALUES %s\n"),
    ;

    private final String method;
    private final String desc;
    private final String sql;

    RdbSqlMethodEnum(String method, String desc, String sql) {
        this.method = method;
        this.desc = desc;
        this.sql = sql;
    }

    public String getMethod() {
        return method;
    }

    public String getDesc() {
        return desc;
    }

    public String getSql() {
        return sql;
    }
}
