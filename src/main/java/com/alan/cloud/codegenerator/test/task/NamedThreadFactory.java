package com.alan.cloud.codegenerator.test.task;

import org.springframework.lang.NonNull;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 10100
 * @date 2020/7/2 20:23
 * @description 自定义ThreadFactory
 **/
public class NamedThreadFactory implements ThreadFactory {

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
        namePrefix = name + "-" +
                poolNumber.getAndIncrement() +
                "-thread-";
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
