package com.owen.disruptor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import sun.misc.Unsafe;

/**
 * Created by owen on 17/6/9.
 */
public class Demo1 {

    private volatile int        circleCount;

    private int                 circleValue;

    private static final Unsafe unsafe = Unsafe.getUnsafe();

    /**
     * 循环5亿次，单线程
     */
    @Test
    public void circle() {
        long t = System.currentTimeMillis();
        circleCount = 50000000;
        circleValue = 0;
        for (long i = 0; i < circleCount; i++) {
            circleValue++;
        }
        t = System.currentTimeMillis() - t;
        System.out.println("circleValue:" + circleValue + ",time:" + t + "ms");
        // time:139
    }

    /**
     * 循环5亿次，单线程，每次循环都会进行加锁
     */
    @Test
    public void circleSynac() {
        long t = System.currentTimeMillis();
        circleCount = 50000000;
        circleValue = 0;

        for (long i = 0; i < circleCount; i++) {
            synchronized (this) {
                circleValue++;
            }
        }
        t = System.currentTimeMillis() - t;
        System.out.println("circleValue:" + circleValue + ",time:" + t + "ms");
        // time:1714
    }

    /**
     * cas
     */
    public void circleCas(){
    
    }

    /**
     * 循环5亿次，双线程
     * 
     * @throws InterruptedException
     */
    @Test
    public void circleMulitiThreads() throws InterruptedException {
        long t = System.currentTimeMillis();
        circleCount = 50000000;
        circleValue = 0;
        ExecutorService executor = Executors.newFixedThreadPool(2);

        CountDownLatch latch = new CountDownLatch(circleCount);
        for (int i = 1; i <= 2; i++) {
            executor.submit(() -> {
                while (circleValue < circleCount) {
                    // System.out.println("threadId:" + Thread.currentThread().getId() + ",before:" + circleValue);
                    synchronized (this) {
                        // System.out.println("threadId:" + Thread.currentThread().getId() + ",after:" + circleValue);
                        if (circleValue < circleCount) {
                            circleValue++;
                            latch.countDown();
                        }
                    }
                }
            });
        }

        latch.await();

        t = System.currentTimeMillis() - t;
        System.out.println("circleValue:" + circleValue + ",time:" + t + "ms");
        // 3020
    }
    


}
