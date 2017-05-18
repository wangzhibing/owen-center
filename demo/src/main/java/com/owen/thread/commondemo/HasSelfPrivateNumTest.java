package com.owen.thread.commondemo;


/**
 * println是异步的
 * @author owen
 *
 */
public class HasSelfPrivateNumTest {
    public static void main(String[] args) {
        HasSelfPrivateNum hasSelfPrivateNum1 = new HasSelfPrivateNum();
        for(int i = 1;i<=100;i++){
            ThreadA thread1 = new ThreadA(hasSelfPrivateNum1,"i="+i);
            thread1.start();
        }
    }
}

class HasSelfPrivateNum {

    private int num = 0;

    synchronized public void addI(String userName,String name) {
        System.out.println(name);
        try {
            if(name.equals("aaa")){
                Thread.sleep(500l);
            }
            System.err.println("ThreadName:"+ name +" num =" + num);
        } catch (Exception e) {
        }
    }
}

class ThreadA extends Thread{
    private HasSelfPrivateNum hasSelfPrivateNum;
    private String name;
    
    public ThreadA(HasSelfPrivateNum hasSelfPrivateNum,String name){
        super();
        this.hasSelfPrivateNum = hasSelfPrivateNum;
        this.name = name;
    }
    
    public void run(){
        super.run();
        hasSelfPrivateNum.addI("a",name);
    }
}

class ThreadB extends Thread{
    private HasSelfPrivateNum hasSelfPrivateNum;
    private String name ;
    
    public ThreadB(HasSelfPrivateNum hasSelfPrivateNum){
        super();
        this.hasSelfPrivateNum = hasSelfPrivateNum;
        this.name=name;
    }
    
    public void run(){
        super.run();
        hasSelfPrivateNum.addI("b",name);
    }
}
