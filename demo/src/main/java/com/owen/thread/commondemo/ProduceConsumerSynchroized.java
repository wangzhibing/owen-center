package com.owen.thread.commondemo;


class ProduceConsumerSynchroized {
	
	public static void main(String[] args) {
		Resource resource = new Resource();
	    Producer produce = new Producer(resource);
	    Consumer consumer = new Consumer(resource);
		
		Thread t1 = new Thread(produce);
		Thread t2 = new Thread(produce);
		Thread t3 = new Thread(consumer);
		Thread t4 = new Thread(consumer);
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
	}
	
}


class Resource{
	
	private String name;
	
	private int count = 1;
	
	private boolean flag = false;
	
	public synchronized void set(String name){
		while(flag){
			try {
				this.wait();
			} catch (Exception e) {
			}
		}
		this.name = name+"--"+count++;
		System.out.println(Thread.currentThread().getName()+"....生产者..."+this.name);
	    flag = true;
	    this.notify();
	}
	
	public synchronized void out(){
		while(!flag){
			try {
				this.wait();
			} catch (Exception e) {
			}
		}
		System.out.println(Thread.currentThread().getName()+"..........消费者..."+this.name);
		flag = false;
		this.notify();
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
class Producer implements Runnable{
	
	private Resource resource;
	
	public Producer(Resource resource){
		this.resource = resource;
	}
	
	public void run(){
		while(true){
			resource.set("++商品++");
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
class Consumer implements Runnable{
private Resource resource;
	
	public Consumer(Resource resource){
		this.resource = resource;
	}
	
	public void run(){
		while(true){
			resource.out();
		}
	}
}
