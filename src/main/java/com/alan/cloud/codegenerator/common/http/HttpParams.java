package com.alan.cloud.codegenerator.common.http;

import lombok.Data;
import org.apache.http.client.config.RequestConfig;

import java.util.Map;

/**
 * @author 10100
 * @description http请求信息
 */
@Data
public class HttpParams {
    /**
     * 完整的请求地址
     */
    public String url;

    /**
     * 请求host
     */
    public String host;

    /**
     * 请求端口
     */
    public int port;

    /**
     * 请求路径
     */
    public String path;

    /**
     * 请求协议
     */
    public String protocol;

    /**
     * 请求参数(?key=value)
     */
    public String params;

    /**
     * 请求header
     */
    public Map<String, String> headerMap;

    /**
     * 请求内容
     */
    public Map<String, Object> contentMap;

    /**
     * 请求配置
     */
    public RequestConfig requestConfig;

    public HttpParams() {
    }

    public HttpParams(String host) {
        this.host = host;
    }

    public HttpParams(String url, Map<String, String> headerMap) {
        this.url = url;
        this.headerMap = headerMap;
    }

    public HttpParams(String url, String params, Map<String, String> headerMap) {
        this.url = url;
        this.params = params;
        this.headerMap = headerMap;
    }

    public HttpParams(String url, Map<String, Object> contentMap, Map<String, String> headerMap) {
        this.url = url;
        this.contentMap = contentMap;
        this.headerMap = headerMap;
    }

    public HttpParams(String host, String path, String params) {
        this.host = host;
        this.path = path;
        this.params = params;
    }

    public HttpParams(String host, String path, String params, Map<String, String> headerMap) {
        this.host = host;
        this.path = path;
        this.params = params;
        this.headerMap = headerMap;
    }

    public HttpParams(String host, int port, String path, String params) {
        this.host = host;
        this.port = port;
        this.path = path;
        this.params = params;
    }

    public HttpParams(String host, int port, String path, String params, Map<String, String> headerMap) {
        this.host = host;
        this.port = port;
        this.path = path;
        this.params = params;
        this.headerMap = headerMap;
    }

    public HttpParams(String host, int port, String path, String protocol, String params) {
        this.host = host;
        this.port = port;
        this.path = path;
        this.protocol = protocol;
        this.params = params;
    }

    public HttpParams(String host, int port, String path, String protocol, String params, Map<String, String> headerMap) {
        this.host = host;
        this.port = port;
        this.path = path;
        this.protocol = protocol;
        this.params = params;
        this.headerMap = headerMap;
    }

    public HttpParams(String host, int port, String path, String protocol, String params, Map<String, String> headerMap, Map<String, Object> contentMap, RequestConfig requestConfig) {
        this.host = host;
        this.port = port;
        this.path = path;
        this.protocol = protocol;
        this.params = params;
        this.headerMap = headerMap;
        this.contentMap = contentMap;
        this.requestConfig = requestConfig;
    }
}
