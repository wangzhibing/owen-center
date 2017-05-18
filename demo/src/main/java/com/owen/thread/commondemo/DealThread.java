package com.owen.thread.commondemo;

/**
 * 1、死锁例子：相互等待对方释放锁，就有可能会出现死锁
 * 2、检测死锁现象 JDK自带工具， 在jdk/bin目录下   执行：jps  然后获取正在运行的线程ID  
 *   然后再执行：jstack -l 线程ID
 *   
 *      1060 DealThread
        1069 Jps
        237
 *   
 *   
    Found one Java-level deadlock:
    =============================
    "Thread-1":
      waiting to lock monitor 0x00007fbcd280b9f8 (object 0x00000007957b6fb0, a java.lang.Object),
      which is held by "Thread-0"
    "Thread-0":
      waiting to lock monitor 0x00007fbcd28092c8 (object 0x00000007957b6fc0, a java.lang.Object),
      which is held by "Thread-1"
    
    Java stack information for the threads listed above:
    ===================================================
    "Thread-1":
            at com.owen.thread.commondemo.DealResource.run(DealThread.java:74)
            - waiting to lock <0x00000007957b6fb0> (a java.lang.Object)
            - locked <0x00000007957b6fc0> (a java.lang.Object)
            at java.lang.Thread.run(Thread.java:745)
    "Thread-0":
            at com.owen.thread.commondemo.DealResource.run(DealThread.java:59)
            - waiting to lock <0x00000007957b6fc0> (a java.lang.Object)
            - locked <0x00000007957b6fb0> (a java.lang.Object)
            at java.lang.Thread.run(Thread.java:745)
    
    Found 1 deadlock.
 * 
 * @author owen
 *
 */
public class DealThread {
    public static void main(String[] args) {
        try {
            DealResource t1 = new DealResource();
            t1.setFlag("a");
            Thread thread1 = new Thread(t1);
            thread1.start();
            Thread.sleep(100);

            t1.setFlag("b");

            Thread thread2 = new Thread(t1);
            thread2.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

/**
 * 死锁资源
 * 
 * @author owen
 *
 */
class DealResource implements Runnable {

    public String userName;

    public Object lock1 = new Object();
    public Object lock2 = new Object();

    public void setFlag(String userName) {
        this.userName = userName;
    }

    public void run() {
        if (userName.equals("a")) {
            synchronized (lock1) {
                try {
                    System.out.println("username=" + userName);
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                synchronized (lock2) {
                    System.out.println("按lock1->lock2代码顺序执行了");
                }
            }
        }

        if (userName.equals("b")) {
            synchronized (lock2) {
                try {
                    System.out.println("username=" + userName);
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                synchronized (lock1) {
                    System.out.println("按lock2->lock1代码顺序执行了");
                }
            }
        }

    }

}
