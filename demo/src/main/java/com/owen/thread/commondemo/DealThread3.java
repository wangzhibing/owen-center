package com.owen.thread.commondemo;

public class DealThread3 {

    public static void main(String[] args) {
        Resources r = new Resources(true);
        Thread t1 = new Thread(r);
        
        r.setFlag(false);
        Thread t2 = new Thread(r);
        
        t1.start();
        t2.start();
    }
}

/***
 * 定义锁
 * 
 * @author owen
 *
 */
class MyLockGroup {

    public static Object lockA = new Object();

    public static Object lockB = new Object();

}

class Resources implements Runnable {

    private boolean flag;

    public Resources(boolean flag) {
        this.flag = flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void run() {
        if (this.flag) {
            synchronized (MyLockGroup.lockA) {
                System.out.println("true: A come in");
                synchronized (MyLockGroup.lockB) {
                    System.out.println("true: B come in");
                }
            }
        } else {
            synchronized (MyLockGroup.lockB) {
                System.out.println("false: B come in");
                synchronized (MyLockGroup.lockA) {
                    System.out.println("false: A come in");
                }
            }
        }
    }
}
