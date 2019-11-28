package com.alan.cloud.codegenerator.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @王合
 * @2019-10-14 13:45:01
 */
@ApiModel("表信息")
@Data
public class TableInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "表名")
    private String tableName;

    @ApiModelProperty(value = "注释")
    private String comments;

    @ApiModelProperty(value = "表字段")
    private List<ColumnInfo> listColumn;
    
}

