package com.geekalliance.taurus.rdb.enums;

/**
 * @author maxuqiang
 */
public enum DataBaseTypeEnum {
    /**
     * 数据库类型枚举
     */
    MYSQL("com.mysql.cj.jdbc.Driver", "jdbc:mysql://${spring.datasource.addr}:${spring.datasource.port}/${spring.datasource.dbname}?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT&allowMultiQueries=true", "3306"),
    SQLSERVER("com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://${spring.datasource.addr}:${spring.datasource.port};DatabaseName=${spring.datasource.dbname}", "1433"),
    ORACLE("com.oracle.jdbc.OracleDriver", "jdbc:oracle:thin:@${spring.datasource.addr}:${spring.datasource.port}:${spring.datasource.dbname}", "1521");
    private String className;

    private String url;

    private String port;

    DataBaseTypeEnum(String className, String url, String port) {
        this.className = className;
        this.url = url;
        this.port = port;
    }

    public String getClassName() {
        return className;
    }

    public String getUrl() {
        return url;
    }

    public String getPort() {
        return port;
    }
}
