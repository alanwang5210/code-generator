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

    /**
     * 列名
     */
    @ApiModelProperty(value = "列名")
    private String colName;

    /**
     * 列类型
     */
    @ApiModelProperty(value = "列类型")
    private String colType;

    /**
     * 列备注
     */
    @ApiModelProperty(value = "列备注")
    private String comments;

    /**
     * 默认值
     */
    @ApiModelProperty(value = "默认值")
    private String defaultValue;

    /**
     * 是否允许为null
     */
    @ApiModelProperty(value = "是否允许为null")
    private boolean isNullable;

    /**
     * 其他
     */
    @ApiModelProperty(value = "其他")
    private String extra;

}

