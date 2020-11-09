package com.alan.cloud.codegenerator.common.lock;


import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;


/**
 * redis分布式锁注解
 *
 * @Author wh
 * @Date 2020/7/6 10:53
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface RedisLock {
    /**
     * redis key
     */
    String lockKey() default "";

    /**
     * 过期时间
     */
    long timeOut() default 5000;

    /**
     * 时间单位
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
}