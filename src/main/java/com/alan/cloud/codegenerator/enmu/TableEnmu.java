package com.alan.cloud.codegenerator.enmu;

/**
 * @author 王合
 * @date 2020-02-10 17:10:13
 */
public enum TableEnmu {

    TABLE_NAME(0, "tableName", "表名"),
    TABLE_COMMENTS(1, "comments", "注释"),
    CREATE_TABLE_SQL(1, "createTableSql", "建表语句");

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

    TableEnmu(Integer code, String fieldName, String msg) {
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

    public static TableEnmu getTableEnmu(String msg) {
        return valueOf(msg);
    }
}
