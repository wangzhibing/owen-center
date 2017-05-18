package com.owen.thread.commondemo;

/**
 * 如果一个类中，有个多个synchronized方法块时(此时获取的锁是.class)，那么访问时是需同步的。
 *     1、synchronized(无static)方法，锁是当前对象，也就是this。 类似代码块synchronized(this)
 *     2、static synchronized方法，锁是当前java类.class 类似 synchronized(TestSynchronized.class)
 *     3、锁对像：监听对象，不可为String  String 是有缓存
 *     
 * 
 * @author owen
 *
 */
public class SynachroizeScope {

    public static void main(String[] args) {
        Resuorce re = new Resuorce();
        Thread1 a = new Thread1(re);
        Thread2 b = new Thread2(re);
        a.setName("A");
        b.setName("B");

        a.start();
        b.start();
    }
}

class Resuorce {
    
    private Object lock = new Object();
    
    public void methodA() {
        synchronized (Resuorce.class) {
            try {
                System.out.println("begin methodA threadName:" + Thread.currentThread().getName());
                Thread.sleep(5000);
                System.out.println("end endTimeA:" + System.currentTimeMillis());
            } catch (Exception e) {
            }
        }
    }

    public static synchronized void methodB() {
            try {
                System.out.println("begin methodB threadName:" + Thread.currentThread().getName());
                Thread.sleep(5000);
                System.out.println("end endTimeB:" + System.currentTimeMillis());
            } catch (Exception e) {
        }
    }
}

class Thread1 extends Thread {

    private Resuorce resource;

    public Thread1(Resuorce resource) {
        this.resource = resource;
    }

    public void run() {
        super.run();
        resource.methodA();
    }
}

class Thread2 extends Thread {

    private Resuorce resource;

    public Thread2(Resuorce resource) {
        this.resource = resource;
    }

    public void run() {
        super.run();
        resource.methodB();
    }
}