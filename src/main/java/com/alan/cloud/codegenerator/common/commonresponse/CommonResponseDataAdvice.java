package com.alan.cloud.codegenerator.common.commonresponse;

import com.alan.cloud.codegenerator.common.enmu.ExceptionTypeEnum;
import com.alibaba.fastjson.JSON;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * 统一响应拦截
 *
 * @author wh
 * @date 2019/4/10 上午10:30
 */

@RestControllerAdvice
public class CommonResponseDataAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        //判断如果该类被注解了@IgnoreResponseAdvice,则该类不使用统一响应
        if (methodParameter.getDeclaringClass().isAnnotationPresent(IgnoreResponseAdvice.class)) {
            return false;
        } else {
            return !Objects.requireNonNull(methodParameter.getMethod()).isAnnotationPresent(IgnoreResponseAdvice.class);
        }
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (Objects.isNull(o)) {
            return HttpResult.error(ExceptionTypeEnum.SYSTEM_EXCEPTION.getType(), ExceptionTypeEnum.SYSTEM_EXCEPTION.getDescribe());
        } else if (o instanceof HttpResult) {
            return o;
        } else if (o instanceof String) {
            return JSON.toJSONString(HttpResult.success(o));
        } else {
            return HttpResult.success(o);
        }
    }
}