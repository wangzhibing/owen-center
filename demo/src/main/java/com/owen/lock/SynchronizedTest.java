package com.owen.lock;

/**
 * Created by Mizlicai on 2019/5/31.
 */
public class SynchronizedTest {

    Object lock = new Object();

    public static void main(String[] args) {

    }


    public synchronized void method1(){


    }

    public void method2(){
        synchronized (lock){
            System.out.println("ok");
        }
    }


}
