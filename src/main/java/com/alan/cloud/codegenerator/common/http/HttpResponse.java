package com.alan.cloud.codegenerator.common.http;

import lombok.Data;

/**
 * @author wh
 * @description http 返回信息
 */
@Data
public class HttpResponse {

    /**
     * code
     */
    public int code;

    /**
     * result
     */
    public String result;
}
