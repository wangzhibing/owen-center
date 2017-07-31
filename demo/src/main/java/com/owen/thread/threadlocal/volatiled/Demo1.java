package com.owen.thread.threadlocal.volatiled;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by owen on 17/6/19.
 */
public class Demo1 {

    int i;

    @Test
    public void circleSynac() {
        long t = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int threadCount = 1; threadCount <= 5; i++) {
            executor.execute(() -> {
                for(int j=0;j<100;j++){
                    if(i > 500){
                        return;
                    }
                    i++;
                    System.out.println(Thread.currentThread().getId()+","+j+","+i);
                }
            });
        }

        t = System.currentTimeMillis() - t;
        System.out.println("time:" + t + "ms");
    }

}
