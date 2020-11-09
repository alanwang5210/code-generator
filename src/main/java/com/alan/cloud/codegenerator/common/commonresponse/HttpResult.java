package com.alan.cloud.codegenerator.common.commonresponse;

import lombok.Data;

/**
 * 统一响应封装实体类
 *
 * @author wh
 * @date 2019-10-13 14:29:55
 */
@Data
public class HttpResult<T> {

    /**
     * 状态标记 true -- 成功 false -- 失败
     */
    private boolean status;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回内容
     */
    private T data;

    /**
     * success
     *
     * @param status  状态
     * @param message 信息
     * @param data    返回数据
     */
    public HttpResult(boolean status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    /**
     * fail
     *
     * @param status  状态
     * @param code    错误类型
     * @param message 信息
     * @param data    返回数据
     */
    public HttpResult(boolean status, Integer code, String message, T data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> HttpResult<T> success() {
        return new HttpResult<>(true, null, null);
    }

    public static <T> HttpResult<T> success(T data) {
        return new HttpResult<>(true, null, data);
    }

    public static <T> HttpResult<T> success(String message) {
        return new HttpResult<>(true, message, null);
    }

    public static <T> HttpResult<T> success(T data, String message) {
        return new HttpResult<>(true, message, data);
    }

    public static <T> HttpResult<T> error() {
        return new HttpResult<>(false, -1, null, null);
    }

    public static <T> HttpResult<T> error(String message) {
        return new HttpResult<>(false, -1, message, null);
    }

    public static <T> HttpResult<T> error(int errorType, String message) {
        return new HttpResult<>(false, errorType, message, null);
    }

    public static <T> HttpResult<T> error(int errorType, T data) {
        return new HttpResult<>(false, errorType, null, data);
    }

    public static <T> HttpResult<T> error(int errorType, String message, T data) {
        return new HttpResult<>(false, errorType, message, data);
    }
}
