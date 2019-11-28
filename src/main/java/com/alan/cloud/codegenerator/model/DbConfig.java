package com.alan.cloud.codegenerator.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@ApiModel(value = "数据库配置信息", description = "")
public class DbConfig implements Serializable {

    private static final long serialVersionUID = 1L;

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
}

