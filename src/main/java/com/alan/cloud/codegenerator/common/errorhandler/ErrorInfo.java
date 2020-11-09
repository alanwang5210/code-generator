package com.alan.cloud.codegenerator.common.errorhandler;

import lombok.Data;

/**
 * 异常信息
 *
 * @author wh
 * @date 2019/4/1 下午1:49
 */

@Data
public class ErrorInfo {

    public static final Integer ERROR_CODE = -1;

    /**
     * 特定错误码
     */
    private Integer code;

    /**
     * 发生时间
     */
    private String time;

    /**
     * 访问Url
     */
    private String url;

    /**
     * 错误信息
     */
    private String error;

    /**
     * 错误类型
     */
    private String errorType;

    /**
     * 错误的堆栈轨迹
     */
    String stackTrace;

    /**
     * 状态码
     */
    private int statusCode;

    /**
     * 状态码
     */
    private String reasonPhrase;
}
