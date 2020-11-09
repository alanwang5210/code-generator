package com.alan.cloud.codegenerator.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author 王合
 * @date 2020-01-31 17:12:36
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "数据库信息", description = "数据库信息")
public class DatabaseInfo implements Serializable {

    @ApiModelProperty(value = "数据库地址")
    public String url;

    @ApiModelProperty(value = "数据库驱动")
    public String driver;

    @ApiModelProperty(value = "数据库名")
    public String schemaName;

    @ApiModelProperty(value = "用户名")
    public String username;

    @ApiModelProperty(value = "数据库类型")
    public String dbType;

    @ApiModelProperty(value = "字符集", notes = "字符集名称")
    public String defaultCharacterSetName;

    @ApiModelProperty(value = "字符集校验名称", notes = "字符集校验名称")
    public String defaultCollationName;

    @ApiModelProperty(value = "数据库版本")
    public String version;

    @ApiModelProperty(value = "架构类型")
    public String compileMachineVersion;

    @ApiModelProperty(value = "操作系统类型")
    public String osVersion;
}
