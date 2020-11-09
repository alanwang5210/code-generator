package com.alan.cloud.codegenerator.common.commonresponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解类,用@ignoreResponseAdvice注解的controller不会被运用统一的响应方式
 *
 * @author wh
 * @date 2019/4/10 上午10:32
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreResponseAdvice {
}
