package com.alan.cloud.codegenerator.common.loginlog.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * ##登录日志
 * </p>
 *
 * @author citybrain
 * @since 2019-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUserLoginLog implements Serializable {

    /**
     * 日志编号
     */
    private Integer id;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 用户编号
     */
    private String userId;

    /**
     * 登录时间
     */
    private Date loginTime;

    /**
     * 登录IP
     */
    private String loginIp;

    /**
     * 登录状态
     */
    private String loginStatus;

    /**
     * 登录异常信息
     */
    private String loginException;

    /**
     * 应用名称
     */
    private String loginAppName;

    /**
     * 登录状态代码
     */
    private Integer loginCode;
}
