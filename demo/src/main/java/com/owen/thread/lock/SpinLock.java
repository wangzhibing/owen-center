package com.owen.thread.lock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 1、自旋锁 自旋锁是采用让当前线程不停地的在循环体内执行实现的， 当循环的条件被其他线程改变时 才能进入临界区
 * 2、自选锁的其他种类，有三种常见的锁形式:TicketLock ，CLHlock 和MCSlock
 * 
 * @author owen
 *
 */
public class SpinLock {

    private AtomicReference<Thread> sign = new AtomicReference<Thread>();

    public void lock() {
        Thread current = Thread.currentThread();
        while (!sign.compareAndSet(null, current)) {

        }
    }

    public void unlock() {
        Thread current = Thread.currentThread();
        sign.compareAndSet(current, null);
    }
}
