package com.owen.thread.commondemo;

/***
 * 使用DCL双检查锁机制来实现多线程环境中的延迟加载单例涉及模式
 * @author owen
 *
 */
public class DCLSIngletonTest {
    
    public static void main(String[] args) {
        MyThread myThread1 = new MyThread();
        MyThread myThread2 = new MyThread();
        MyThread myThread3 = new MyThread();
        
        myThread1.start();
        myThread2.start();
        myThread3.start();
    }
}

class DCLSIngleton {
    
    private volatile static DCLSIngleton _DCLSIngleton;

    private DCLSIngleton(){
    }

    //使用双检测机制来解决问题，既保证了不需要同步代码的异步执行性。
    //又保证了单例的效果
    
    public static DCLSIngleton getInstance(){
        try {
            if(_DCLSIngleton == null){
                synchronized(DCLSIngleton.class){
                    if(_DCLSIngleton == null){
                        _DCLSIngleton = new DCLSIngleton();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return _DCLSIngleton;
    }
}

class MyThread extends Thread{
    public void run (){
        System.out.println("单例对象hashcode:"+DCLSIngleton.getInstance().hashCode());
    }
}

