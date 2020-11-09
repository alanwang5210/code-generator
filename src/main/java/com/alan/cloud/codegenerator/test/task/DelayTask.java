package com.alan.cloud.codegenerator.test.task;

import lombok.Data;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author ：10100
 * @date ：2020/6/9 19:15
 * @desc 任务延时queen
 */
@Data
public class DelayTask implements Delayed {

    /**
     * 任务id
     */
    final private String taskId;

    /**
     * 计划执行任务时间（ms）
     */
    final private long expire;

    /**
     * 构造延时任务
     *
     * @param taskId 比对任务id
     * @param expire 计划执行任务时间（ms）
     */
    public DelayTask(String taskId, long expire) {
        this.taskId = taskId;
        this.expire = expire;
    }


    @Override
    public String toString() {
        return "{" + "taskId:" + taskId + "," + "expire:" + new Date(expire) + "}";
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.expire - System.currentTimeMillis(), unit);
    }

    @Override
    public int compareTo(Delayed o) {
        long delta = getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
        return (int) delta;
    }
}
