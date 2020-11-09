package com.alan.cloud.codegenerator.common.OperationLog.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * #操作日志
 * </p>
 *
 * @author citybrain
 * @since 2019-04-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysOperationLog implements Serializable {

    /**
     * 日志编号
     */

    private Integer id;
    /**
     * 操作模块
     */

    private String logModule;
    /**
     * 请求IP
     */

    private String requestIp;
    /**
     * 用户编号
     */

    private String userId;
    /**
     * 用户名称
     */

    private String userName;
    /**
     * 请求URL
     */

    private String path;
    /**
     * 请求方法
     */

    private String method;
    /**
     * 请求参数
     */

    private String params;
    /**
     * 请求结果
     */

    private String result;
    /**
     * 请求时间
     */

    private Date startTime;
    /**
     * 结束时间
     */

    private Date endTime;
    /**
     * 执行时间 毫秒
     */

    private Long execTime;
    /**
     * 请求异常信息
     */

    private String logException;
    /**
     * 创建时间
     */

    private Date createDate;
    /**
     * 操作说明
     */

    private String operationDesc;

    /**
     * 操作状态
     */
    private boolean status;

}
