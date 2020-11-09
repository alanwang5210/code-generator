package com.alan.cloud.codegenerator.test.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.*;

/**
 * @author 10100
 * @date 2020/6/9 20:07
 * @desc 比对任务管理
 */
@Component
@Slf4j
public class TaskManager implements CommandLineRunner {

    private String jarId = "111";

    private String serverAddr = "222";

    private String dataId = "333";

    private String group = "444";
    /**
     * 延时队列
     **/
    private final DelayQueue<DelayTask> delayQueue = new DelayQueue<>();

    /**
     * 执行线程池
     */
    private final ExecutorService executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
            60L, TimeUnit.SECONDS, new SynchronousQueue<>(), new NamedThreadFactory("contrast-object-sync"));

    /**
     * 记录任务的map
     **/
    private final ConcurrentHashMap<String, DelayTask> queueMap = new ConcurrentHashMap<>();

    /**
     * 加入到延时队列中
     *
     * @param task 延时任务
     */
    public void put(DelayTask task) {

        /*如果已经有任务， 替换原有任务*/
        if (Objects.nonNull(this.getDelayedTask(task.getTaskId()))) {
            DelayTask oldTask = this.getDelayedTask(task.getTaskId());
            delayQueue.remove(oldTask);
            queueMap.remove(task.getTaskId());
        }
        delayQueue.put(task);
        queueMap.put(task.getTaskId(), task);
    }

    /**
     * 加入到延时队列中
     *
     * @param taskId 任务id
     * @param time   延时时间
     * @return void
     */
    public void put(String taskId, long time) {
        DelayTask task = new DelayTask(taskId, time);
        this.put(task);
    }


    /**
     * 取消延时任务
     *
     * @param task 延时任务
     * @return boolean
     */
    public void remove(DelayTask task) {
        delayQueue.remove(task);
    }

    /**
     * 取消延时任务
     *
     * @param taskId 任务id
     * @return boolean
     */
    public void remove(String taskId) {
        DelayTask task = this.getDelayedTask(taskId);
        this.remove(task);
    }

    /**
     * 获取延迟执行的任务
     *
     * @param taskId 任务id
     * @return boolean
     */
    public DelayTask getDelayedTask(String taskId) {
        return this.queueMap.get(taskId);
    }

    @Override
    public void run(String... args) {
        Executors.newSingleThreadExecutor().execute(this::executeThread);
    }

    /**
     * 延时任务执行线程
     *
     * @return void
     */
    @SuppressWarnings("InfiniteLoopStatement")
    private void executeThread() {
        while (true) {
//            try {
//                DelayTask task = delayQueue.take();
//                this.queueMap.remove(task.getTaskId());
//                TaskRunner runner = new TaskRunner(task.getTaskId(), jarId, serverAddr, dataId, group);
//                executor.execute(new TaskRunner(task.getTaskId(), jarId, serverAddr, dataId, group));
//            } catch (Exception e) {
//                log.error(e.getMessage());
//            }
        }
    }
}
