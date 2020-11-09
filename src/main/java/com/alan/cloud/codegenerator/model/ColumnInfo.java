package com.alan.cloud.codegenerator.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @王合
 * @2019-10-14 13:45:01
 */

@Data
@ApiModel(value = "表字段", description = "数据表字段信息")
public class ColumnInfo implements Serializable {

    private static final long serialVersionUID = 1;

    @ApiModelProperty(value = "列名")
    private String colName;

    @ApiModelProperty(value = "列类型")
    private String colType;

    @ApiModelProperty(value = "列备注")
    private String comments;

    @ApiModelProperty(value = "默认值")
    private String defaultValue;

    @ApiModelProperty(value = "是否允许为null")
    private boolean isNullable;

    @ApiModelProperty(value = "其他信息")
    private String extra;

    @ApiModelProperty(value = "table schema", notes = "字段表所属数据库")
    private String tableSchema;

    @ApiModelProperty(value = "表名", notes = "字段所属表名")
    private String tableName;

    @ApiModelProperty(value = "字段顺序", notes = "字段顺序", example = "1")
    private int ordinalPosition;

    @ApiModelProperty(value = "字段是否允许为null", notes = "字段是否允许为null")
    private String nullable;

    @ApiModelProperty(value = "字段类型", notes = "字段类型")
    private String dataType;

    @ApiModelProperty(value = "字符最大长度", notes = "字符最大长度", example = "1")
    private int characterMaximumLength;

    @ApiModelProperty(value = "字节长度", notes = "字节长度", example = "1")
    private int characterOctetLength;

    @ApiModelProperty(value = "数字最大位数", notes = "数字最大位数", example = "1")
    private int numericPrecision;

    @ApiModelProperty(value = "数字小数位数", notes = "数字小数位数", example = "1")
    private int numericScale;

    @ApiModelProperty(value = "时间精度", notes = "时间精度")
    private int datetimePrecision;

    @ApiModelProperty(value = "字符集", notes = "字符集名称")
    private String characterSetName;

    @ApiModelProperty(value = "字符集校验名称", notes = "字符集校验名称")
    private String collationName;

    @ApiModelProperty(value = "关键列", notes = "关键列[NULL|MUL|UNI|PRI]")
    private String columnKey;

    @ApiModelProperty(value = "字段操作权限", notes = "字段操作权限[select|select,insert,update,references]")
    private String privileges;

    @ApiModelProperty(value = "列类型表达式", notes = "列类型表达式")
    private String generationExpression;

    @ApiModelProperty(value = "地理数据类型", notes = "地理数据类型", example = "1")
    private int srsId;
}

