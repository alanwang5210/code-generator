package com.alan.cloud.codegenerator.model.qo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 数据库表查询对象
 *
 * @王合
 * @2019-11-25 00:38:30
 */
@ApiModel("数据库表查询对象")
@Data
public class TableQO {

    /**
     * 列名
     */
    @ApiModelProperty(value = "数据库ID")
    private Long id;

    /*
     * 当前页码
     */
    @ApiModelProperty(value = "当前页码", example = "1")
    private Integer current;

    /*
     * 每页记录数
     */
    @ApiModelProperty(value = "每页记录数", example = "20")
    private Integer size;
}
