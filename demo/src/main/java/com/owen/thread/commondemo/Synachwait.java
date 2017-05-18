package com.owen.thread.commondemo;

public class Synachwait {
    
    
    public static Class lock = Synachwait.class;  
    
    public static void main(String[] args) throws Exception {  
        new TestThread1().start();  
        new TestThread1().start();  
  
        Thread.sleep(3000);   
        // NotifyWaitTest.lock.notifyAll(); //# poing 1  
        synchronized (Synachwait.lock) {  
            try {  
                System.out.println(Thread.currentThread().getName() + " sent notification all");  
                Synachwait.lock.notifyAll();  
                Thread.sleep(6000); 
                System.out.println("main....");
  
                // System.out.println(Thread.currentThread().getName() + " sent notification 1");  
                // NotifyWaitTest.lock.notify();  
                // System.out.println(Thread.currentThread().getName() + " sent notification 2");  
                // Thread.sleep(3000);  
                // NotifyWaitTest.lock.notify();  
                // System.out.println(Thread.currentThread().getName() + " sent notification over");  
  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
    }  

}


class TestThread1 extends Thread {  
    public void run() {  
        synchronized (Synachwait.lock) {  
            try {  
                
                System.out.println(Thread.currentThread().getName() + " wait for notification");  
                //Thread.sleep(6000); 
                //System.out.println(Thread.currentThread().getName() + " 等待6妙之后进行wait");  
                Synachwait.lock.wait();  
  
                System.out.println(Thread.currentThread().getName() + " wake up");  
                for (int i = 0; i < 3; i++) {  
                    Thread.sleep(1000);  
                    System.out.println(Thread.currentThread().getName() + " doing " + i);  
                }  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
            System.out.println(Thread.currentThread().getName() + " finished");  
            //Synachwait.lock.notifyAll();
        }  
    }  
}  
  
class TestThread2 extends Thread {  
  
    public void run() {  
        synchronized (Synachwait.lock) {  
            System.out.println(Thread.currentThread().getName() + " wait for notification");  
            try {  
                Synachwait.lock.wait();  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
        }  
        System.out.println(Thread.currentThread().getName() + " wake up");  
        for (int i = 0; i < 3; i++) {  
            try {  
                Thread.sleep(1000);  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
            System.out.println(Thread.currentThread().getName() + " doing " + i);  
        }  
    }  
}  
