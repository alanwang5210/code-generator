package com.alan.cloud.codegenerator.common.http;

import com.alan.cloud.codegenerator.common.errorhandler.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wh
 * @date 2019/3/1 下午6:23
 */
@Slf4j
public class HttpClient {

    private final static String CONTENT_TYPE = "Content-Type";

    /**
     * http 发送post 请求
     * 功能详细描述
     *
     * @param httpParams http 请求参数
     * @return com.alan.cloud.codegenerator.common.http.HttpResponse http 请求返回信息
     * @author 王合
     */
    public static HttpResponse doPost(HttpParams httpParams) {
        //组装请求URL
        URI uri = populateUri(httpParams);
        // 创建Post请求
        HttpPost httpPost = new HttpPost(uri);
        processRequest(httpPost, httpParams);
        return execute(httpPost);
    }


    /**
     * http 发送post 请求 - 带文件
     *
     * @param httpParams http 请求参数
     * @param file       file文件
     * @return com.alan.cloud.codegenerator.common.http.HttpResponse 请求返回信息
     * @author 王合
     */
    public static HttpResponse doPost(HttpParams httpParams, File file) {

        //组装请求URL
        URI uri = populateUri(httpParams);

        // 创建Post请求
        HttpPost httpPost = new HttpPost(uri);

        //设置超时时间
        RequestConfig requestConfig;
        if (httpParams.getRequestConfig() != null) {
            requestConfig = httpParams.getRequestConfig();
        } else {
            requestConfig = RequestConfig.custom()
                    .setConnectTimeout(2000).setConnectionRequestTimeout(1000)
                    .setSocketTimeout(5000).build();
        }
        httpPost.setConfig(requestConfig);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();

        /* example for setting a HttpMultipartMode */
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        /* example for adding an image part */
        FileBody fileBody = new FileBody(file);
        builder.addPart("file", fileBody);
        builder.addPart("name", new StringBody(httpParams.getParams(), ContentType.TEXT_PLAIN));
        HttpEntity entity = builder.build();

        httpPost.setEntity(entity);

        //返回消息
        return execute(httpPost);
    }

    /**
     * 处理URI
     *
     * @param httpParams httpParams
     * @return java.net.URI
     */
    public static URI populateUri(HttpParams httpParams) {
        try {
            URIBuilder uriBuilder = new URIBuilder();
            //设置请求协议
            if (StringUtils.isNotEmpty(httpParams.getProtocol())) {
                uriBuilder.setScheme(httpParams.getProtocol());
            } else {
                //默认使用http
                uriBuilder.setScheme("http");
            }
            //设置端口
            if (httpParams.getPort() > 0) {
                uriBuilder.setPort(httpParams.getPort());
            }
            //设置请求路径
            if (StringUtils.isNotEmpty(httpParams.getPath())) {
                uriBuilder.setPath(httpParams.getPath());
            }

            if (StringUtils.isNotEmpty(httpParams.getParams())) {
                uriBuilder.setCustomQuery(httpParams.getParams());
            }
            uriBuilder.setHost(httpParams.getUrl());

            return uriBuilder.build();

        } catch (URISyntaxException e) {
            log.error(e.getMessage());
            throw new BaseException();
        }
    }

    /**
     * 处理请求
     *
     * @param httpPost   httpPost
     * @param httpParams httpParams
     * @return void
     */
    public static void processRequest(HttpPost httpPost, HttpParams httpParams) {
        try {
            //设置超时时间
            RequestConfig requestConfig;
            if (httpParams.getRequestConfig() != null) {
                requestConfig = httpParams.getRequestConfig();
            } else {
                requestConfig = RequestConfig.custom()
                        .setConnectTimeout(2000).setConnectionRequestTimeout(1000)
                        .setSocketTimeout(5000).build();
            }
            httpPost.setConfig(requestConfig);

            /*设置参数*/
            if (StringUtils.isNotEmpty(httpParams.getParams())) {
                //设置请求body
                StringEntity stringEntity;
                stringEntity = new StringEntity(httpParams.getParams(), "UTF-8");
                httpPost.setEntity(stringEntity);
            }

            if (httpParams.getContentMap() != null && httpParams.getContentMap().size() > 0) {
                List<NameValuePair> nvps = new ArrayList<>();
                Map<String, Object> params = httpParams.getContentMap();
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    nvps.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            }

            // 设置header
            if (httpParams.getHeaderMap() != null && httpParams.getHeaderMap().size() > 0) {
                Map<String, String> headerMap = httpParams.getHeaderMap();
                for (String key : headerMap.keySet()) {
                    httpPost.setHeader(key, headerMap.get(key));
                }
            }

            if (httpPost.getHeaders(CONTENT_TYPE) == null || httpPost.getHeaders(CONTENT_TYPE).length <= 0) {
                httpPost.setHeader(CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf8");
            }

        } catch (Exception e) {
            throw new BaseException(e);
        }
    }

    /**
     * http 发送get请求
     * 功能详细描述
     *
     * @param httpParams http 请求参数
     * @return com.alan.cloud.codegenerator.common.http.HttpResponse http 请求返回信息
     * @author 王合
     */
    public static HttpResponse doGet(HttpParams httpParams) {

        //组装请求URL
        URI uri = populateUri(httpParams);
        // 创建httpGet远程连接实例
        HttpGet httpGet = new HttpGet(uri);

        //设置超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(5000).build();
        httpGet.setConfig(requestConfig);

        return execute(httpGet);
    }

    /**
     * http 发送get请求
     * 使用url get 请求
     *
     * @param url http 请求参数
     * @return HttpResponse http 请求返回信息
     * @author 王合
     */
    public static HttpResponse doGet(String url) {

        // 创建httpGet远程连接实例
        HttpGet httpGet = new HttpGet(url);
        //设置超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(5000).build();
        httpGet.setConfig(requestConfig);

        return execute(httpGet);
    }

    public static HttpResponse execute(HttpUriRequest request) {
        // 获得Http客户端
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 响应模型
        CloseableHttpResponse response = null;
        //返回消息
        HttpResponse httpResponse = new HttpResponse();
        try {
            //由客户端执行(发送)Post请求
            response = httpClient.execute(request);
            // 从响应模型中获取响应实体
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity responseEntity = response.getEntity();
            String result = EntityUtils.toString(responseEntity);
            httpResponse.setCode(statusCode);
            httpResponse.setResult(result);
            return httpResponse;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            httpResponse.setCode(-1);
            return httpResponse;
        } finally {
            close(response, httpClient);
        }
    }

    /**
     * 关闭http 连接
     * 功能详细描述
     *
     * @param response   HttpResponse
     * @param httpClient HttpClient
     * @author 王合
     */
    public static void close(CloseableHttpResponse response, CloseableHttpClient httpClient) {
        // 关闭资源
        if (null != response) {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
        }
        if (null != httpClient) {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
        }
    }

    /**
     * 拼接对象请求URL
     *
     * @param args 需要拼接的对象
     * @return java.lang.String 请求URL
     * @author 王合
     */
    public static String parseUrlPair(Object... args) {

        Map<String, Object> map = new TreeMap<>();

        for (Object o : args) {
            Class<?> c = o.getClass();
            List<Field> fieldList = new ArrayList<>();
            while (c != null) {
                fieldList.addAll(new ArrayList<>(Arrays.asList(c.getDeclaredFields())));
                c = c.getSuperclass();
            }
            Field[] fields = new Field[fieldList.size()];
            fieldList.toArray(fields);

            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                Object value = null;
                try {
                    if (field.get(o) != null && StringUtils.equals(field.getType().getName(), "java.util.Date")) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        value = simpleDateFormat.format(field.get(o));
                    } else {
                        value = field.get(o);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (value != null) {
                    map.put(name, value);
                }
            }
        }

        Set<Map.Entry<String, Object>> set = map.entrySet();
        Iterator<Map.Entry<String, Object>> it = set.iterator();
        StringBuilder sb = new StringBuilder();
        while (it.hasNext()) {
            Map.Entry<String, Object> e = it.next();
            sb.append(e.getKey()).append("=").append(e.getValue()).append("&");
        }
        if (sb.length() > 0) {
            return sb.deleteCharAt(sb.length() - 1).toString();
        } else {
            return "";
        }
    }
}
