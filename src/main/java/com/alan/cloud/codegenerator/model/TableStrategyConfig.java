package com.alan.cloud.codegenerator.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @王合
 * @2019-09-18 23:09:08
 */

@Data
@Accessors(chain = true)
@ApiModel(value = "数据表生成code策略", description = "")
public class TableStrategyConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID", example = "1")
    private Long id;

    @ApiModelProperty(value = "数据库id", example = "1")
    private Long dbId;

    @ApiModelProperty(value = "数据库表名")
    private String tableName;

    @ApiModelProperty(value = "前缀")
    private String prefix;

    @ApiModelProperty(value = "model 名称")
    private String modelName;

    @ApiModelProperty(value = "model 父包名")
    private String modelParentName;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "entity 名称")
    private String entityName;

    @ApiModelProperty(value = "mapper 名称")
    private String mapperName;

    @ApiModelProperty(value = "xml 名称")
    private String xmlName;

    @ApiModelProperty(value = "service 名称")
    private String serviceName;

    @ApiModelProperty(value = "service impl 名称")
    private String serviceImplName;

    @ApiModelProperty(value = "controller 名称")
    private String controllerName;

    @ApiModelProperty(value = "entity package 名称")
    private String entityPackage;

    @ApiModelProperty(value = "service package 名称")
    private String servicePackage;

    @ApiModelProperty(value = "service impl package 名称")
    private String serviceImplPackage;

    @ApiModelProperty(value = "mapper package 名称")
    private String mapperPackage;

    @ApiModelProperty(value = "controller package 名称")
    private String controllerPackage;
}
