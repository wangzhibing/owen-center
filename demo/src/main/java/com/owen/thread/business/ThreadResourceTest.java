package com.owen.thread.business;

import java.util.ArrayList;
import java.util.List;


public class ThreadResourceTest {
	
    public static void main(String[] args) {
    	GoldResource goldBusiness = new GoldResource();
    	InsertRecThread in = new InsertRecThread(goldBusiness);
    	OutRecThread out = new OutRecThread(goldBusiness);
    	
    	Thread t1 = new Thread(in);
    	Thread t2 = new Thread(in);
    	Thread t3 = new Thread(in);
    	Thread t4 = new Thread(in);
    	Thread t5 = new Thread(in);
    	
    	Thread t6 = new Thread(out);
    	Thread t7 = new Thread(out);
    	Thread t8 = new Thread(out);
    	Thread t9 = new Thread(out);
    	Thread t10 = new Thread(out);
    	
    	t1.start();
    	t2.start();
    	t3.start();
    	t4.start();
    	t5.start();
    	t6.start();
    	t7.start();
    	t8.start();
    	t9.start();
    	t10.start();
    }
}


class InsertRecThread implements Runnable{
	
	private int c = 1;
	
	private GoldResource goldResource;
	
	public InsertRecThread(GoldResource goldResource){
		this.goldResource = goldResource;
	}

	//@Override
	public void run() {
		List<String> tempList = new ArrayList<String>();
//		for(int i=((c-1)*10) ; i< c*10 ; i++){
//			tempList.add("str"+i);
//		}
//		c++;
		goldResource.insertList(tempList);
	}
}


class OutRecThread implements Runnable{
	
	private GoldResource goldResource;
	
	public OutRecThread(GoldResource goldResource){
		this.goldResource = goldResource;
	}

	//@Override
	public void run() {
		goldResource.outList();
	}
}

