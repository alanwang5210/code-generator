package com.alan.cloud.codegenerator.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author 10100
 * @date 2020/7/2 19:41
 * @description：
 **/
@Component
public class ScheduleA {
    
    /**
     * 定时更新任务状态
     *
     * @return void
     */
    //@Scheduled(cron = "2/4 * * * * ?")
    public void updateTaskStatus() throws InterruptedException {
        Thread.sleep(5000);

        System.out.println(LocalDateTime.now() + "------ A");
    }
}
