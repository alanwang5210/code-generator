package com.alan.cloud.codegenerator.common.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 10100
 * @date 2020/7/2 19:56
 * @description 定时任务多线程配置
 **/
@Configuration
public class ScheduleConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        //scheduled定时执行任务的时候是在一个单线程中，如果有多个任务，其中一个任务执行时间过长，则有可能会导致其他后续任务被阻塞直到该任务执行完成。
        taskRegistrar.setScheduler(new ScheduledThreadPoolExecutor(10, new NamedThreadFactory("Schedule")));
    }

    public static class NamedThreadFactory implements ThreadFactory {

        private final ThreadGroup threadGroup;

        private final AtomicInteger threadNumber = new AtomicInteger(1);

        public final String namePrefix;

        public NamedThreadFactory(String name) {
            SecurityManager s = System.getSecurityManager();
            threadGroup = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            if (null == name || "".equals(name.trim())) {
                name = "pool";
            }
            AtomicInteger poolNumber = new AtomicInteger(1);
            namePrefix = name + "-" + poolNumber.getAndIncrement() + "-thread-";
        }

        @Override
        public Thread newThread(@NonNull Runnable r) {
            Thread t = new Thread(threadGroup, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }
}

