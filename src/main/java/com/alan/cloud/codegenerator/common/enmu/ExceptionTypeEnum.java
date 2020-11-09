package com.alan.cloud.codegenerator.common.enmu;

/**
 * 异常枚举类
 *
 * @author 王合
 * @date 2019-10-13 14:34:06
 */
public enum ExceptionTypeEnum {

    /**
     * 系统抛出的不可预知的异常
     */
    SYSTEM_EXCEPTION(10000, "系统异常"),

    /**
     * 业务流程中的异常
     */
    BUSINESS_EXCEPTION(100001, "业务异常"),

    /**
     * 数据库相关的异常
     */
    DB_EXCEPTION(10002, "数据库异常"),

    /**
     * redis相关的异常
     */
    REDIS_EXCEPTION(10003, "Redis异常"),

    /**
     * ElasticSearch相关的异常
     */
    ES_EXCEPTION(10004, "ElasticSearch异常"),

    /**
     * 消息队列相关的异常
     */
    MQ_EXCEPTION(10005, "消息队列异常"),

    /**
     * 上传文件过大
     */
    MAX_UPLOAD_SIZE_EXCEEDED_EXCEPTION(10006, "上传文件过大"),

    /**
     * 请求参数格式有误
     */
    METHOD_ARGUMENT_NOT_VALID_EXCEPTION(10007, "请求参数格式有误"),

    /**
     * 业务流程中的异常
     */
    OTHER_EXCEPTION(100099, "其他异常");


    int type;

    String describe;

    ExceptionTypeEnum(int type, String describe) {
        this.type = type;
        this.describe = describe;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}

