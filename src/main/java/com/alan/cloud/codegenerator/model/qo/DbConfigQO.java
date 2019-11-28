package com.alan.cloud.codegenerator.model.qo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @王合
 * @2019-11-25 16:01:47
 */
@ApiModel("数据库配置查询对象")
@Data
public class DbConfigQO {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "数据库地址")
    private String url;

    @ApiModelProperty(value = "数据库驱动")
    private String driver;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "数据库名")
    private String schemaName;

    @ApiModelProperty(value = "数据库类型")
    private String dbType;

    @ApiModelProperty(value = "当前页码", example = "1")
    private Long current;

    @ApiModelProperty(value = "每页记录数", example = "20")
    private Long size;
}
