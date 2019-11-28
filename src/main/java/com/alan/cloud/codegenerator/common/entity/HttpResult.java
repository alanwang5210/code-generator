package com.alan.cloud.codegenerator.common.entity;

import com.alan.cloud.codegenerator.common.enmu.ExceptionTypeEnum;
import lombok.Data;

/**
 * @王合
 * @2019-10-13 14:29:55
 */
@Data
public class HttpResult<T> {

    /**
     * 状态 true 成功 error 失败
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 错误类型
     */
    private Integer errorType;

    /**
     * 返回内容
     */
    private T data;

    public HttpResult() {

    }

    public HttpResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public HttpResult(Integer code, String message, Integer errorType, T data) {
        this.code = code;
        this.message = message;
        this.errorType = errorType;
        this.data = data;
    }

    public static <T> HttpResult<T> success() {
        return new HttpResult<>(0, null, null);
    }

    public static <T> HttpResult<T> success(T data) {
        return new HttpResult<>(0, null, data);
    }

    public static <T> HttpResult<T> success(T data, String message) {
        return new HttpResult<>(0, message, data);
    }

    public static <T> HttpResult<T> success(T data, String message, Object... args) {
        return new HttpResult<>(0, String.format(message, args), data);
    }

    public static <T> HttpResult<T> error(String message) {
        return new HttpResult<>(-1, message, ExceptionTypeEnum.BIZ_EXCEPTION.getType(), null);
    }

    public static <T> HttpResult<T> error(String message, Object... args) {
        return new HttpResult<>(-1, String.format(message, args), ExceptionTypeEnum.BIZ_EXCEPTION.getType(), null);
    }

    public static <T> HttpResult<T> error(int errorType, String message) {
        return new HttpResult<>(-1, message, errorType, null);
    }

    public static <T> HttpResult<T> error(int errorType, String message, Object... args) {
        return new HttpResult<>(-1, String.format(message, args), errorType, null);
    }
}
