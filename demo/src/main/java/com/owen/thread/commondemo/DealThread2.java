package com.owen.thread.commondemo;

/**
 * 死锁：同步里面套同步 这个思想好
 * 
 * @Title: DeadLock
 * @Description: XXXXX
 * @Author: owen
 * @Since: 2014-9-24
 * @Version: 1.1.0
 */
public class DealThread2 implements Runnable{
	
	private boolean flag;
	
	public DealThread2(boolean flag){
		this.flag = flag;
	}
	
	public void run(){
		if(flag){
			synchronized (MyLock.lockA) {
				System.out.println("if lockA ");
				synchronized (MyLock.lockB) {
					System.out.println("if lockB");
				}
			}
		}
		else{
			synchronized (MyLock.lockB) {
				System.out.println("else lockB ");
				synchronized (MyLock.lockA) {
					System.out.println("else lockA");
				}
			}
		}
	}
}


class MyLock{
	static Object lockA = new Object();
	static Object lockB = new Object();
}

class DeadLockTest{
	public static void main(String[] args) {
		Thread t1 = new Thread(new DealThread2(true));
		Thread t2 = new Thread(new DealThread2(false));
		t1.start();
		t2.start();
	}
}
