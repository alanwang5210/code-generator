package com.alan.cloud.codegenerator.common.excpetion;

import com.alan.cloud.codegenerator.common.enmu.ExceptionTypeEnum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wanghe
 * @date 2019-08-20
 */
@Data
public class BaseException extends RuntimeException {

    /**
     * 异常类型
     */
    protected int type;

    /**
     * 异常描述
     */
    protected String describe;

    public BaseException() {
        super();
    }

    public BaseException(ExceptionTypeEnum exceptionType, String message) {
        this(exceptionType.getType(), message);
    }

    public BaseException(int exceptionType, String message) {
        super(message);
        type = exceptionType;
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(Throwable cause, String message) {
        super(message, cause);
    }

    public BaseException(Throwable cause, String message, Object... param) {
        super(String.format(message, param), cause);
    }

    public BaseException(String message, Object... param) {
        super(String.format(message, param));
    }
}
