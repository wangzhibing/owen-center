package com.owen.thread.commondemo;

/**
 * 使用静态内置类对象实现单例模式
 * @author owen
 *
 */
public class InnerSingletonTest{
    public static void main(String[] args) {
        MyInnerThread myThread1 = new MyInnerThread();
        MyInnerThread myThread2 = new MyInnerThread();
        MyInnerThread myThread3 = new MyInnerThread();
        
        myThread1.start();
        myThread2.start();
        myThread3.start();
    }
}

class InnerSingleton{
    
    private static class MyInnerHandler{
        private static InnerSingleton innerSingleton = new InnerSingleton();
    }
    
    private InnerSingleton(){}
    
    public static InnerSingleton getInstance(){
        return MyInnerHandler.innerSingleton;
    }
}

class MyInnerThread extends Thread{
    public void run (){
        System.out.println("单例对象hashcode:"+InnerSingleton.getInstance().hashCode());
    }
}
