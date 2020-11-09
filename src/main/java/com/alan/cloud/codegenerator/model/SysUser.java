package com.alan.cloud.codegenerator.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @王合
 * @2019-10-15 16:26:07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("用户")
public class SysUser {

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "头像地址")
    private String headImgUrl;

    @ApiModelProperty(value = "性别", example = "1")
    private int sex;

    @ApiModelProperty(value = "出生日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "邮箱地址")
    private String email;

    @ApiModelProperty(value = "地区")
    private String address;

    @ApiModelProperty(value = "职位")
    private String position;

    @ApiModelProperty(value = "简介")
    private String intro;

    @ApiModelProperty(value = "用户状态" , example = "1")
    private int status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

}