package com.owen.thread.safe;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * volatile 可见性
 *          原子性，不保证的
 * 
 * 一旦一个共享变量（类的成员变量、类的静态成员变量）被volatile修饰之后，那么就具备了两层语义：
　　1）保证了不同线程对这个变量进行操作时的可见性，即一个线程修改了某个变量的值，这新值对其他线程来说是立即可见的。
　　2）禁止进行指令重排序。
 * **/
public class VolatileTest {

    private volatile int inc = 0;

    public void increase() {
        inc++;
    }

    public static void main(String[] args) {
        final VolatileTest test = new VolatileTest();
        for(int i =0;i<2;i++){
            new Thread(){
                public void run(){
                    for(int j=0;j<500;j++){
                        test.increase();
                    }
                }
            }.start();
        }
        
        //保证前面的线程都执行完
        while(Thread.activeCount() > 1){
            Thread.yield();
        }
        
        System.out.println(test.inc);
    }
}


//
///***
// * synchronized
// * @author owen
// *
// */
//class VolatileTest1 {
//
//    private int inc = 0;
//
//    public synchronized void increase() {
//        inc++;
//    }
//
//    public static void main(String[] args) {
//        final VolatileTest1 test = new VolatileTest1();
//        for(int i =0;i<10;i++){
//            new Thread(){
//                public void run(){
//                    for(int j=0;j<1000;j++){
//                        test.increase();
//                    }
//                }
//            }.start();
//        }
//        
//        while(Thread.activeCount() > 1){
//            Thread.yield();
//        }
//        
//        System.out.println(test.inc);
//    }
//}
//
//
///***
// * Lock
// * @author owen
// *
// */
//class VolatileTest2 {
//
//    private int inc = 0;
//    private Lock lock = new ReentrantLock();
//
//    public void increase() {
//        lock.lock();
//        try {
//            inc++;
//        }finally{
//            lock.unlock();
//        }
//       
//    }
//
//    public static void main(String[] args) {
//        final VolatileTest2 test = new VolatileTest2();
//        for(int i =0;i<10;i++){
//            new Thread(){
//                public void run(){
//                    for(int j=0;j<1000;j++){
//                        test.increase();
//                    }
//                }
//            }.start();
//        }
//        
//        while(Thread.activeCount() > 1){
//            Thread.yield();
//        }
//        
//        System.out.println(test.inc);
//    }
//}
//
//
///***
// * atomicInteger
// * @author owen
// * 在java 1.5的java.util.concurrent.atomic包下提供了一些原子操作类，
// *   即对基本数据类型的 自增（加1操作），自减（减1操作）、以及加法操作（加一个数），
// *   减法操作（减一个数）进行了封装，保证这些操作是原子性操作。
// *   atomic是利用CAS来实现原子性操作的（Compare And Swap），
// *   CAS实际上是利用处理器提供的CMPXCHG指令实现的，而处理器执行CMPXCHG指令是一个原子性操作。
// *
// */
//class VolatileTest3 {
//
//    private AtomicInteger inc = new AtomicInteger();
//
//    public void increase() {
//        //inc.getAndAdd(2);
//        inc.getAndIncrement();
//    }
//
//    public static void main(String[] args) {
//        final VolatileTest3 test = new VolatileTest3();
//        for(int i =0;i<10;i++){
//            new Thread(){
//                public void run(){
//                    for(int j=0;j<1000;j++){
//                        test.increase();
//                    }
//                }
//            }.start();
//        }
//        
//        while(Thread.activeCount() > 1){
//            Thread.yield();
//        }
//        
//        System.out.println(test.inc);
//    }
//    
//}
//
//
//
//
//
//
//
//
