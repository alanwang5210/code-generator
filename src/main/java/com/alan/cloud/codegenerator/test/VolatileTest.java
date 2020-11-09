package com.alan.cloud.codegenerator.test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: 10100
 * @License: (C) Copyright 2020-2050,  Corporation Limited.
 * @Contact: wang.he@cpe-smartcity.com
 * @Date: 2020/9/11 15:53
 * @Description:
 */

public class VolatileTest {

    public static volatile int race = 0;

    public static AtomicInteger i = new AtomicInteger(0);

    private static final int THREADS_COUNT = 20;

    public static void increase() {
        race++;
        i.incrementAndGet();
    }

    public static void main(String[] args) throws InterruptedException {
        Thread.currentThread().getThreadGroup().list();

        Thread[] threads = new Thread[THREADS_COUNT];
        for (int i = 0; i < THREADS_COUNT; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10000; i++) {
                        increase();
                    }
                }
            });
            threads[i].start();
        }


        while (Thread.activeCount() > 1) {
            Thread.yield();
        }
        System.out.println(race);
        System.out.println(i);
    }
}