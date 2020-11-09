package com.alan.cloud.codegenerator.common.OperationLog.common;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)

public @interface OperationLog {

    /*操作模块*/
    String module() default "";

    /*操作说明*/
    String desc() default "";
}
