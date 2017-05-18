package com.owen.thread.commondemo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


class ProduceConsumerReentrantLock {
	
	public static void main(String[] args) {
		Resource2 resource2 = new Resource2();
	    Producer2 produce2 = new Producer2(resource2);
	    Consumer2 consumer2 = new Consumer2(resource2);
		
		Thread t1 = new Thread(produce2);
		Thread t2 = new Thread(produce2);
		Thread t3 = new Thread(consumer2);
		Thread t4 = new Thread(consumer2);
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
	}
	
}


class Resource2{
	
	private String name; 
	
	private int count = 1;
	
	private boolean flag = false;
	
	private Lock lock = new ReentrantLock();
	private Condition proCondition = lock.newCondition();
	private Condition conCondition = lock.newCondition();
	
	public void set(String name){
		try {
			lock.lock();
			while(flag){
				proCondition.await();
			}
			this.name = name+"--"+count++;
			System.out.println(Thread.currentThread().getName()+"....生产者..."+this.name);
		    flag = true;
		    conCondition.signal();
		}
		catch (Exception e) {} 
		finally{
			lock.unlock();
		}
	}
	
	public synchronized void out(){
		try {
			lock.lock();
			while(!flag){
				conCondition.await();
			}
			System.out.println(Thread.currentThread().getName()+"..........消费者..."+this.name);
			flag = false;
			proCondition.signal();
		} 
		catch (Exception e) {}
		finally{
			lock.unlock();
		}
	}
}

/**
 * 生产者
 * @Title: Produce
 * @Description: XXXXX
 * @Author: owen
 * @Since: 2014-9-24
 * @Version: 1.1.0
 */
class Producer2 implements Runnable{
	
	private Resource2 resource2;
	
	public Producer2(Resource2 resource2){
		this.resource2 = resource2;
	}
	
	public void run(){
		while(true){
			resource2.set("++商品++");
		}
	}
}

/**
 * 消费者
 * @Title: consume
 * @Description: XXXXX
 * @Author: owen
 * @Since: 2014-9-24
 * @Version: 1.1.0
 */
class Consumer2 implements Runnable{
private Resource2 resource2;
	
	public Consumer2(Resource2 resource2){
		this.resource2 = resource2;
	}
	
	public void run(){
		while(true){
			resource2.out();
		}
	}
}


