package com.owen.disruptor;

import com.google.common.collect.Queues;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by owen on 17/6/8.
 */
public class SingleThread {

    //共有变量
    private int threadCount = 10;
    private int size = 20;
    private LinkedBlockingDeque deque;
    RingBuffer<LongEvent> ringBuffer = null;
    CountDownLatch latch = null;

    /**
     * 单线程测试
     */
    @Test
    public void test() {
        long t = System.currentTimeMillis();
        for (long i = 1; i <= threadCount; i++) {
            //开启多个线程发布事件
            for (long j = 1; j <= size; j++) {

                LongEvent event = new LongEvent();
                event.setValue(i * j);
            }
        }

        t = System.currentTimeMillis() - t;
        System.out.println("time:" + t);
        System.out.println("QPS:" + threadCount * size / t);
    }


    public class MyRunnable2 implements Runnable {
        private long id;

        public MyRunnable2(long id) {
            this.id = id;
        }

        @Override
        public void run() {
            for (long j = 1; j <= size; j++) {
                LongEvent event = new LongEvent();
                event.setValue(id * j);
                try {

                    deque.put(event);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 多线程
     *
     * @throws InterruptedException
     */
    @Test
    public void test2() throws InterruptedException {
        deque = Queues.newLinkedBlockingDeque();
        ExecutorService exec = Executors.newFixedThreadPool(threadCount + 5);
        latch = new CountDownLatch(threadCount * size);
        long t = System.currentTimeMillis();
        for (long i = 1; i <= threadCount; i++) {
            exec.submit(new MyRunnable2(i));
        }

        for (int i = 1; i <= 5; i++) {
            exec.submit(() -> {
                while (!deque.isEmpty()) {
                    try {
                        deque.take();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    latch.countDown();
                }
            });
        }

        latch.await();
        t = System.currentTimeMillis() - t;
        System.out.println("time:" + t);
        System.out.println("QPS:" + threadCount * size / t);
    }


    public class MyRunnable3 implements Runnable {
        private long id;

        public MyRunnable3(long id) {
            this.id = id;
        }

        @Override
        public void run() {
            for (long i = 1; i <= size; i++) {
                long sequence = ringBuffer.next();
                try {
                    //生产者：生产对象
                    System.out.println("sequence:" + sequence);
                    ringBuffer.get(sequence).setValue(id * i);
                } finally {
                    //把生产对象发布至环中，发布这个区块的数据使handler(consumer)可见
                    System.out.println("sequence_publish:" + sequence);
                    ringBuffer.publish(sequence);
                }
            }
        }
    }

    /**
     * Disruptor
     *
     * @throws InterruptedException
     */
    @Test
    public void test3() throws InterruptedException {
        int bufferSize = 1048576;



        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(LongEvent::new, bufferSize, Executors.defaultThreadFactory());
        disruptor.handleEventsWith((LongEvent event, long sequence, boolean ednOfBatch) -> {
            //消费
            System.out.println("sequence:"+sequence+",event:"+event.toString() );
            latch.countDown();
        });

        disruptor.start();

        ringBuffer = disruptor.getRingBuffer();


        latch = new CountDownLatch(threadCount * size);

        ExecutorService exec = Executors.newFixedThreadPool(threadCount);
        long t = System.currentTimeMillis();
        for (int i = 1; i <= threadCount; i++) {
            //生产
            exec.submit(new MyRunnable3(i));
        }

        latch.await();
        System.out.println("ccccccccccc");
        t = System.currentTimeMillis() - t;
        System.out.println("time:" + t);
        System.out.println("QPS:" + threadCount * size / t);
    }


}
