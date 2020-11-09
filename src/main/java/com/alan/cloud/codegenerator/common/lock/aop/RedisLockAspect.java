package com.alan.cloud.codegenerator.common.lock.aop;

import com.alan.cloud.codegenerator.common.lock.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wh
 * @description Redis锁RedisLock注解的切面类
 * @date 2020.07.06 10:55
 */
@Aspect
@Component
@Slf4j
public class RedisLockAspect {


    private static final Integer MAX_RETRY_COUNT = 3;
    private static final String LOCK_KEY = "lockKey";
    private static final String TIME_OUT = "timeOut";

    private static final int PROTECT_TIME = 5000;

    /**
     * redis相关
     */
    private static final String LOCK_SUCCESS = "OK";
    private static final Long RELEASE_SUCCESS = 1L;

    private final JedisPool jedisPool;

    public RedisLockAspect(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }


    @Pointcut("@annotation(com.alan.cloud.codegenerator.common.lock.RedisLock)")
    public void redisLockAspect() {
    }

    @Around("redisLockAspect()")
    public Object lockAroundAction(ProceedingJoinPoint proceeding) {

        Object result = null;
        //获取redis锁
        String requestId = System.currentTimeMillis() + "";
        boolean flag = this.getLock(proceeding, requestId, 0);
        if (flag) {
            try {
                result = proceeding.proceed();
            } catch (Throwable throwable) {
                throw new RuntimeException("执行发生异常" + throwable.getMessage(), throwable);
            } finally {
                // 删除锁
                this.delLock(proceeding, requestId);
            }
        } else {
            log.info("其他系统正在执行此项任务");
        }
        return result;
    }


    /**
     * jedis尝试获取分布式锁
     *
     * @param lockKey    锁
     * @param requestId  请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    private boolean tryGetDistributedLock(String lockKey, String requestId, long expireTime) {
        boolean res = false;
        try (Jedis jedis = jedisPool.getResource()) {
            SetParams params = new SetParams();
            /*SET IF NOT EXIST*/
            params.nx();
            /*过期时间*/
            params.px(expireTime);
            /*原子操作 setNx 并设置过期时间*/
            String result = jedis.set(lockKey, requestId, params);
            if (LOCK_SUCCESS.equals(result)) {
                res = true;
            }
        } catch (Exception e) {
            log.error("分布式加锁异常:" + e.getMessage());
            res = false;
        }
        return res;
    }

    /**
     * jedis释放分布式锁
     *
     * @param lockKey   锁
     * @param requestId 请求标识
     */
    private void releaseDistributedLock(String lockKey, String requestId) {
        try (Jedis jedis = jedisPool.getResource()) {
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
        } catch (Exception e) {
            log.error("分布式锁解锁异常:" + e.getMessage());
        }
    }

    /**
     * 获取锁
     */
    private boolean getLock(ProceedingJoinPoint proceeding, String requestId, int count) {
        //获取注解中的参数
        Map<String, Object> annotationArgs = this.getAnnotationArgs(proceeding);
        String key = (String) annotationArgs.get(LOCK_KEY);
        long expire = (long) annotationArgs.get(TIME_OUT);

        if (StringUtils.isEmpty(key)) {
            throw new RuntimeException("RedisLock锁名key未设置");
        }
        if (tryGetDistributedLock(key, requestId, expire)) {
            return true;
        } else {
            /*执行重试*/
            if (count < MAX_RETRY_COUNT) {
                count++;
                getLock(proceeding, requestId, count);
            }
            return false;
        }
    }

    /**
     * 删除锁
     */
    private void delLock(ProceedingJoinPoint proceeding, String requestId) {
        Map<String, Object> annotationArgs = this.getAnnotationArgs(proceeding);
        String key = (String) annotationArgs.get(LOCK_KEY);
        releaseDistributedLock(key, requestId);
    }

    /**
     * 获取锁参数
     */
    private Map<String, Object> getAnnotationArgs(ProceedingJoinPoint proceeding) {
        MethodSignature methodSignature = (MethodSignature) proceeding.getSignature();
        Method method = methodSignature.getMethod();
        RedisLock redisLock = method.getAnnotation(RedisLock.class);

        Map<String, Object> result = new HashMap<>(16);
        result.put(LOCK_KEY, redisLock.lockKey());
        result.put(TIME_OUT, redisLock.timeUnit().toSeconds(redisLock.timeOut()));
        return result;
    }
}
