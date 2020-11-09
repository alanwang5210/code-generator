package com.alan.cloud.codegenerator.enmu;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author 王合
 * @date 2020-02-13 15:46:11
 */
public enum ColumnEnmu {

    TABLE_NAME(0, "colName", "列名"),
    TABLE_COMMENTS(1, "comments", "列备注");

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

    ColumnEnmu(Integer code, String fieldName, String msg) {
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

    public static ColumnEnmu getColumnEnmu(String msg) {
        return valueOf(msg);
    }
}