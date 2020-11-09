package com.alan.cloud.codegenerator.common.errorhandler;

import com.alan.cloud.codegenerator.common.commonresponse.HttpResult;
import com.alan.cloud.codegenerator.common.enmu.ExceptionTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一异常处理
 *
 * @author wh
 * @date 2019-11-24 20:20:37
 */
@Slf4j
@RestControllerAdvice
public class DefaultGlobalExceptionHandler {

    @ExceptionHandler({Exception.class})
    public HttpResult<String> defaultErrorHandler(HttpServletRequest request, Exception e) {
        logError(request, e);
        return HttpResult.error(ExceptionTypeEnum.SYSTEM_EXCEPTION.getType(), ExceptionTypeEnum.SYSTEM_EXCEPTION.getDescribe(), e.getMessage());
    }

    @ExceptionHandler({BaseException.class})
    public HttpResult<String> baseErrorHandler(HttpServletRequest request, BaseException e) {
        logError(request, e);
        return HttpResult.error(e.getType(), e.getDescribe(), e.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public HttpResult<String> methodArgumentNotValidErrorHandler(HttpServletRequest request, Exception e) {
        logError(request, e);
        return HttpResult.error(ExceptionTypeEnum.METHOD_ARGUMENT_NOT_VALID_EXCEPTION.getType(), ExceptionTypeEnum.METHOD_ARGUMENT_NOT_VALID_EXCEPTION.getDescribe(), e.getMessage());
    }

    @ExceptionHandler({MaxUploadSizeExceededException.class})
    public HttpResult<String> maxUploadSizeExceededErrorHandler(HttpServletRequest request, Exception e) {
        logError(request, e);
        return HttpResult.error(ExceptionTypeEnum.MAX_UPLOAD_SIZE_EXCEEDED_EXCEPTION.getType(), ExceptionTypeEnum.MAX_UPLOAD_SIZE_EXCEEDED_EXCEPTION.getDescribe(), e.getMessage());
    }

    private static void logError(HttpServletRequest request, Exception e) {
        log.error("请求{}发生错误被全局异常捕获，错误信息：{}", request.getRequestURI(), e.getMessage());
        log.error("异常堆栈", e);
    }
}
