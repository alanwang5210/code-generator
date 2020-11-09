package com.alan.cloud.codegenerator.test.task;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 10100
 * @date 2020/7/2 19:43
 * @description：
 **/
@Component
public class ScheduleB {

    private final TaskManager taskManager;

    private long count = 0L;

    ScheduledThreadPoolExecutor executors = new ScheduledThreadPoolExecutor(1);

    public ScheduleB(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public static void main(String[] args) {

        long count = 111L;
        System.out.println(String.format("task %s put %s", count, LocalDateTime.now().toString()));


        ScheduledThreadPoolExecutor executors = new ScheduledThreadPoolExecutor(2);

        executors.schedule(new TaskRunner(String.valueOf(count), "", "", "", ""), 5, TimeUnit.SECONDS);
        executors.schedule(new TaskRunner(String.valueOf(count + 1), "", "", "", ""), 5, TimeUnit.SECONDS);
    }

    /**
     * 定时更新任务状态
     *
     * @return void
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void updateTaskStatus() {

        taskManager.put(String.valueOf(count) , 0);
        //System.out.println(String.format("task %s put %s", count, LocalDateTime.now().toString()));
        //executors.schedule(new TaskRunner(String.valueOf(count), "", "", "", ""), 5, TimeUnit.SECONDS);
        count++;
    }
}