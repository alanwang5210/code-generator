package com.alan.cloud.codegenerator.common.OperationLog.common;

import com.alan.cloud.codegenerator.common.OperationLog.model.SysOperationLog;
import com.alan.cloud.codegenerator.common.OperationLog.service.SysOperationLogService;
import com.alan.cloud.codegenerator.common.enmu.ExceptionTypeEnum;
import com.alan.cloud.codegenerator.common.errorhandler.BaseException;
import com.alan.cloud.codegenerator.common.loginlog.model.UserInfo;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
@Slf4j
public class OperationAspect {

    /**
     * 日志信息服务
     */
    @Autowired
    SysOperationLogService sysOperationLogService;

    /**
     * 添加@OperationLog 标签的方法或者类会进入方法
     *
     * @param
     * @return void
     * @author 王合
     * @exception/throws
     */
    @Pointcut("@annotation(com.alan.cloud.codegenerator.common.OperationLog.common.OperationLog)")
    public void logPointcut() {
    }


    /**
     * 日志信息切片
     *
     * @param process 切片
     * @return java.lang.Object 切点方法执行结果
     * @author 王合
     * @exception/throws
     */
    @Around("logPointcut()")
    public Object logHandler(ProceedingJoinPoint process) {
        SysOperationLog sysOperationLog = new SysOperationLog();
        Date date = new Date();

        /*获取请求Request*/
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        long startTime = System.currentTimeMillis();
        long costTime;
        long endTime;
        Throwable error = null;

        MethodSignature methodSignature = (MethodSignature) process.getSignature();

        /*获取执行方法相关信息*/
        Method method = methodSignature.getMethod();

        Annotation operationAnno = method.getAnnotation(OperationLog.class);

        //方法说明
        String desc = ((OperationLog) operationAnno).desc();
        //操作模块
        String operationModule = ((OperationLog) operationAnno).module();

        String methodName = method.getName();
        String className = method.getDeclaringClass().getName();
        Object[] args = process.getArgs();
        StringBuilder params = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            params.append(args[i].toString());
            params.append(";");
        }
        Object result = null;
        String exception = "";
        boolean status = true;
        try {
            /*执行目标方法*/
            result = process.proceed();
        } catch (Throwable throwable) {
            status = false;
            error = throwable;
            exception = throwable.getClass() + ":" + throwable.getMessage();
        }
        endTime = System.currentTimeMillis();
        costTime = endTime - startTime;

        /*获取登录用户信息*/
        Object obj = SecurityContextHolder.getContext().getAuthentication().getDetails();
        UserInfo dahuaUserInfo = new UserInfo();
        if (obj != null) {
            try {
                dahuaUserInfo = (UserInfo) obj;

            } catch (Exception e) {
                //TODO
            }
        }
        sysOperationLog.setUserName(dahuaUserInfo.getUserName());
        sysOperationLog.setUserId(dahuaUserInfo.getUserCode());
        sysOperationLog.setParams(params.toString());
        sysOperationLog.setLogModule(operationModule);
        sysOperationLog.setLogException(exception);
        sysOperationLog.setExecTime(costTime);
        sysOperationLog.setEndTime(new Date(endTime));
        sysOperationLog.setCreateDate(date);
        sysOperationLog.setRequestIp(request.getRemoteAddr());
        sysOperationLog.setMethod(methodName);
        sysOperationLog.setOperationDesc(desc);
        sysOperationLog.setPath(request.getServletPath());
        sysOperationLog.setStartTime(new Date(startTime));
        sysOperationLog.setResult(JSON.toJSONString(result));
        sysOperationLog.setStatus(status);
        sysOperationLogService.save(sysOperationLog);

        if (error != null) {
            log.error(error.getMessage());
            throw new BaseException(ExceptionTypeEnum.SYSTEM_EXCEPTION, error.getMessage(), error);
        }

        return result;
    }
}
