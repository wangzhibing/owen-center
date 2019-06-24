package com.owen.atomic;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicIntegerTest1 implements Runnable {
    
    private static final AtomicLong netSerialNum = new AtomicLong();
    
    private int k =0;
    
    private volatile int j = 0;

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ":" +netSerialNum.get()+","+ netSerialNum.getAndIncrement()+","+netSerialNum.get()+ ","+ (k++) + ","+(j++));
        System.out.println(netSerialNum.get());
        try {
            //Thread.sleep(1000);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    
    public static void main(String[] args) {
        AtomicIntegerTest1 test1 = new AtomicIntegerTest1();
        for(int i=0;i<100;i++){
            new Thread(test1, "Thread"+i).start();
        }
    }
    
  
    
    
    
}
