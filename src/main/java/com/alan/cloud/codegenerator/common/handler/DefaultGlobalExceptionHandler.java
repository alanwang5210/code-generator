package com.alan.cloud.codegenerator.common.handler;

import com.alan.cloud.codegenerator.common.enmu.ExceptionTypeEnum;
import com.alan.cloud.codegenerator.common.entity.HttpResult;
import com.alan.cloud.codegenerator.common.excpetion.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @王合
 * @2019-11-24 20:20:37
 */
@Slf4j
@RestControllerAdvice
public class DefaultGlobalExceptionHandler {

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public HttpResult defaultErrorHandler(HttpServletRequest request, Exception e) {
        logError(request, e);
        return HttpResult.error(ExceptionTypeEnum.SYSTEM_EXCEPTION.getType(), "系统内部异常，请联系开发人员");
    }

    @ExceptionHandler({BaseException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public HttpResult baseErrorHandler(HttpServletRequest request, BaseException e) {
        logError(request, e);
        return HttpResult.error(e.getType(), e.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    @ResponseStatus(HttpStatus.OK)
    public HttpResult methodArgumentNotValidErrorHandler(HttpServletRequest request, Exception e) {
        BindingResult bindingResult;
        if (e instanceof MethodArgumentNotValidException) {
            bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
        } else {
            bindingResult = ((BindException) e).getBindingResult();
        }

        String errorMessage = Optional.ofNullable(bindingResult.getFieldError())
                .map(fieldError -> fieldError.getField() + fieldError.getDefaultMessage())
                .orElse("请求参数格式有误");
        logError(request, errorMessage);
        return HttpResult.error(errorMessage);
    }

    @ExceptionHandler({MaxUploadSizeExceededException.class})
    public HttpResult maxUploadSizeExceededErrorHandler(HttpServletRequest request, Exception e) {
        logError(request, e);
        return HttpResult.error("上传文件过大！");
    }

    private static void logError(HttpServletRequest request, Exception e) {
        logError(request, e.getMessage());
        log.error("异常堆栈", e);
    }

    private static void logError(HttpServletRequest request, String errorMessage) {
        log.error("请求{}发生错误被全局异常捕获，错误信息：{}", request.getRequestURI(), errorMessage);
    }
}
