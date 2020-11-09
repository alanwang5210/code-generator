package com.alan.cloud.codegenerator.common.errorhandler;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

/**
 * 异常信息处理
 *
 * @author wh
 * @date 2019/4/1 下午1:49
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class ErrorInfoBuilder implements HandlerExceptionResolver, Ordered {


    /**
     * 错误KEY
     */
    private final static String ERROR_NAME = "error";

    /**
     * 错误配置(ErrorConfiguration)
     */
    private ErrorProperties errorProperties;

    public ErrorProperties getErrorProperties() {
        return errorProperties;
    }

    public void setErrorProperties(ErrorProperties errorProperties) {
        this.errorProperties = errorProperties;
    }

    /**
     * 错误构造器 (Constructor) 传递配置属性：server.xx -> server.error.xx
     */
    public ErrorInfoBuilder(ServerProperties serverProperties) {
        this.errorProperties = serverProperties.getError();
    }

    /**
     * 构建错误信息.(ErrorInfo)
     *
     * @param request http 请求
     * @return com.alan.cloud.codegenerator.common.errorhandler.ErrorInfo
     */
    public ErrorInfo getErrorInfo(HttpServletRequest request) {

        return getErrorInfo(request, getError(request));
    }

    /**
     * 构建错误信息.(ErrorInfo)
     *
     * @param request http 请求
     * @param error   异常
     * @return com.alan.cloud.codegenerator.common.errorhandler.ErrorInfo 错误信息
     * @author 王合
     */
    public ErrorInfo getErrorInfo(HttpServletRequest request, Throwable error) {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setCode(ErrorInfo.ERROR_CODE);
        errorInfo.setErrorType(error.getClass().getName());
        errorInfo.setTime(LocalDateTime.now().toString());
        errorInfo.setUrl(request.getRequestURL().toString());
        errorInfo.setError(error.getMessage());
        errorInfo.setStatusCode(getHttpStatus(request).value());
        errorInfo.setReasonPhrase(getHttpStatus(request).getReasonPhrase());
        errorInfo.setStackTrace(getStackTraceInfo(error, isIncludeStackTrace(request)));
        return errorInfo;
    }

    /**
     * 获取错误.(Error/Exception)
     *
     * @param request http 请求
     * @return java.lang.Throwable 错误信息
     * @author 王合
     */
    public Throwable getError(HttpServletRequest request) {
        //根据HandlerExceptionResolver接口方法来获取错误.
        Throwable error = (Throwable) request.getAttribute(ERROR_NAME);
        //根据Request对象获取错误.
        if (error == null) {
            error = (Throwable) request.getAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE);
        }
        //当获取错误非空,取出RootCause.
        if (error != null) {
            while (error instanceof ServletException && error.getCause() != null) {
                error = error.getCause();
            }
        }//当获取错误为null,此时我们设置错误信息即可.
        else {
            String message = (String) request.getAttribute(WebUtils.ERROR_MESSAGE_ATTRIBUTE);
            if (StringUtils.isEmpty(message)) {
                HttpStatus status = getHttpStatus(request);
                message = "Unknown Exception But " + status.value() + " " + status.getReasonPhrase();
            }
            error = new Exception(message);
        }
        return error;
    }

    /**
     * 获取通信状态(HttpStatus)
     *
     * @param request 参数说明
     * @return org.springframework.http.HttpStatus 通信状态
     * @author 王合
     */
    public HttpStatus getHttpStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute(WebUtils.ERROR_STATUS_CODE_ATTRIBUTE);
        try {
            return statusCode != null ? HttpStatus.valueOf(statusCode) : HttpStatus.INTERNAL_SERVER_ERROR;
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    /**
     * 获取堆栈轨迹(StackTrace)
     *
     * @param error 异常信息
     * @param flag  参数说明
     * @return java.lang.String 是否包含堆栈轨迹
     * @author 王合
     */
    public String getStackTraceInfo(Throwable error, boolean flag) {
        if (!flag) {
            return "omitted";
        }
        StringWriter stackTrace = new StringWriter();
        error.printStackTrace(new PrintWriter(stackTrace));
        stackTrace.flush();
        return stackTrace.toString();
    }

    /**
     * 判断是否包含堆栈轨迹.(isIncludeStackTrace)
     *
     * @param request http 请求
     * @return boolean 是否包含堆栈轨迹
     * @author 王合
     */
    public boolean isIncludeStackTrace(HttpServletRequest request) {

        //读取错误配置(server.error.include-stacktrace=NEVER)
        ErrorProperties.IncludeStacktrace includeStacktrace = errorProperties.getIncludeStacktrace();

        //情况1：若IncludeStacktrace为ALWAYS
        if (includeStacktrace == ErrorProperties.IncludeStacktrace.ALWAYS) {
            return true;
        }
        //情况2：若请求参数含有trace
        if (includeStacktrace == ErrorProperties.IncludeStacktrace.ON_PARAM) {
            String parameter = request.getParameter("appropriate");
            return parameter != null && !"false".equals(parameter.toLowerCase());
        }
        //情况3：其它情况
        return false;
    }

    /**
     * 保存错误/异常.
     *
     * @param request  http 请求
     * @param response http 响应
     * @param handler  the executed handler
     * @param ex       参数说明
     * @return org.springframework.web.servlet.ModelAndView a corresponding {@code ModelAndView} to forward to,
     * @author 王合
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, @Nullable Object handler, Exception ex) {
        request.setAttribute(ERROR_NAME, ex);
        return null;
    }

    /**
     * 提供优先级 或用于排序
     *
     * @return int 优先级
     * @author 王合
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
