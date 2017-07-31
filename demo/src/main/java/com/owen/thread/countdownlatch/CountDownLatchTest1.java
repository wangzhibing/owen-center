package com.owen.thread.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by owen on 17/7/4.
 */
public class CountDownLatchTest1 {


    // 模拟了100米赛跑，10名选手已经准备就绪，只等裁判一声令下。当所有人都到达终点时，比赛结束。
    public static void main(String[] args) throws InterruptedException {

        // 开始的倒数锁
        final CountDownLatch begin = new CountDownLatch(1);

        // 结束的倒数锁
        final CountDownLatch end = new CountDownLatch(10);

        AtomicInteger errorAtomicCount = new AtomicInteger();

        // 十名选手
        final ExecutorService exec = Executors.newFixedThreadPool(10);

        for (int index = 0; index < 10; index++) {
            final int NO = index + 1;
            exec.submit(new MyRunnable(NO, errorAtomicCount, begin,end) {
            });
        }
        System.out.println("Game Start");
        // begin减一，开始游戏
        begin.countDown();
        // 等待end变为0，即所有选手到达终点
        //end.await(6, TimeUnit.SECONDS);

        try {
            System.out.println("value :" + end.await(6, TimeUnit.SECONDS));
        } catch (Exception e) {
            System.out.println("等待时长过长");
        }


        System.out.println("errorAtomicCount,af"+errorAtomicCount.get());
        System.out.println("Game Over");
        exec.shutdown();
    }

}


class MyRunnable implements Runnable {
    private AtomicInteger atomicInteger;
    private CountDownLatch beginLatch;
    private CountDownLatch endLatch;
    private int no;

    public MyRunnable(int no,AtomicInteger atomicInteger, CountDownLatch beginLatch, CountDownLatch endLatch) {
        this.atomicInteger = atomicInteger;
        this.beginLatch = beginLatch;
        this.endLatch = endLatch;
        this.no=no;
    }

    @Override
    public void run() {
        try {
            // 如果当前计数为零，则此方法立即返回。
            // 等待
            beginLatch.await();
            //Thread.sleep((long) (Math.random() * 10000));
            Thread.sleep(6000L);
            atomicInteger.incrementAndGet();

            System.out.println("No." + no + " arrived");
        } catch (InterruptedException e) {
        } finally {
            // 每个选手到达终点时，end就减一
            System.out.println("end.getCount(),before:" + endLatch.getCount());
            endLatch.countDown();
            System.out.println("end.getCount(),after:" + endLatch.getCount());
        }
    }
}
