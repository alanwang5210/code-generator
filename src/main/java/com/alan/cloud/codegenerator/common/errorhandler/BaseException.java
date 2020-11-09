package com.alan.cloud.codegenerator.common.errorhandler;

import com.alan.cloud.codegenerator.common.enmu.ExceptionTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wh
 * @date 2019-08-20
 */
@EqualsAndHashCode(callSuper = true)
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

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(ExceptionTypeEnum exceptionType) {

        super(exceptionType.getDescribe());
        this.type = exceptionType.getType();
        this.describe = exceptionType.getDescribe();
    }

    public BaseException(ExceptionTypeEnum exceptionType, String message) {
        super(exceptionType.getDescribe());
        this.type = exceptionType.getType();
        this.describe = message;
    }

    public BaseException(ExceptionTypeEnum exceptionType, String message, Throwable cause) {
        super(cause);
        this.type = exceptionType.getType();
        this.describe = exceptionType.getDescribe() + ": " + message;
    }

    public BaseException(int type, String message) {
        super(message);
        this.type = type;
        this.describe = message;
    }

    public BaseException(int type, Throwable cause) {
        super(cause);
        this.type = type;
        this.describe = cause.getMessage();
    }

    public BaseException(int type, Throwable cause, String message) {
        super(message, cause);
        this.type = type;
        this.describe = message;
    }
}
