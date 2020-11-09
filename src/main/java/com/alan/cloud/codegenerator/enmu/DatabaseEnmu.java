package com.alan.cloud.codegenerator.enmu;

/**
 * @author 王合
 * @date 2020-01-31 17:50:22
 */
public enum DatabaseEnmu {

    DB_TYPE(0, "dbType", "数据库类型"),
    URL(1, "url", "数据库地址"),
    SCHEMA_NAME(2, "schemaName", "数据库名"),
    USERNAME(3, "username", "用户名"),
    VERSION(4, "version", "数据库版本");

    /*
     * code
     */
    private Integer code;

    /*
     * 字段名
     */
    private String fieldName;

    /*
     * 表头名
     */
    private String note;

    DatabaseEnmu(Integer code, String fieldName, String msg) {
        this.code = code;
        this.fieldName = fieldName;
        this.note = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getNote() {
        return note;
    }

    public static DatabaseEnmu getDatabaseEnmu(String msg) {
        return valueOf(msg);
    }
}
