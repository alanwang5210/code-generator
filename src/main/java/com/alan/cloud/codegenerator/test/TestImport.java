package com.alan.cloud.codegenerator.test;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.util.Arrays;
import java.util.List;

/**
 * @author 10100
 * @date 2020/7/3 8:58
 * @description：
 **/

//@ComponentScan
public class TestImport {

    public static void main(String[] args) {

        Thread a = new Thread(new H());
        a.start();
    }
}
