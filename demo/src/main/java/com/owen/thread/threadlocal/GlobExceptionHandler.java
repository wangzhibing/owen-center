package com.owen.thread.threadlocal;


import java.lang.Thread.UncaughtExceptionHandler;

/**
 * Created by owen on 17/6/16.
 */
public class GlobExceptionHandler {
    public static void main(String[] args) {
        Thread t = new Thread(new AdminThread());
        t.setDefaultUncaughtExceptionHandler(new ExceptionHandler()); //对当前线程设置默认catch
        t.start();
    }
}

class AdminThread implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(2000);
            System.out.println("start ... Exception");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        throw new NullPointerException(); //直接exception
    }
}

class ExceptionHandler implements UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) { //调用此方法来进行，对异常处理，需要实现UncaughtExceptionHandler 接口
        System.out.println("Thread:" + t + " Exception message:" + e);
    }
}
