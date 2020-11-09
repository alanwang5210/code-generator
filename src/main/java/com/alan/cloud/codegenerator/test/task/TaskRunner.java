package com.alan.cloud.codegenerator.test.task;


import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * @author 10100
 * @date 2020/6/11 15:59
 * @desc 内部执行延时任务
 */
@Slf4j
public class TaskRunner implements Runnable {

    private String flinkNacosAddr;

    private String flinkNacosDataId;

    private String flinkNacosGroup;

    static {
        prepareTask();
    }

    /**
     * 任务id
     */
    private final String taskId;

    /**
     * flink jar id
     */
    private final String jarId;

    public TaskRunner(String taskId, String jarId, String flinkNacosAddr, String flinkNacosDataId, String flinkNacosGroup) {
        this.taskId = taskId;
        this.jarId = jarId;
        this.flinkNacosAddr = flinkNacosAddr;
        this.flinkNacosDataId = flinkNacosDataId;
        this.flinkNacosGroup = flinkNacosGroup;
    }

    /**
     * 实例化相关服务
     *
     * @return void
     */
    public static void prepareTask() {
    }

    @Override
    public void run() {
        System.out.println(String.format("task %s execute %s", taskId, LocalDateTime.now().toString()));
    }
}