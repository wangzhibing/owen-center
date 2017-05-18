package com.owen.thread.business;

import java.util.ArrayList;
import java.util.List;


public class ThreadBusinessTest {
	
    public static void main(String[] args) {
    	GoldBusiness goldBusiness = new GoldBusiness();
    	InsertThread in = new InsertThread(goldBusiness);
    	OutThread out = new OutThread(goldBusiness);
    	
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


class InsertThread implements Runnable{
	
	private int c = 1;
	
	private GoldBusiness goldBusiness;
	
	public InsertThread(GoldBusiness goldBusiness){
		this.goldBusiness = goldBusiness;
	}

	//@Override
	public void run() {
		List<String> tempList = new ArrayList<String>();
//		for(int i=((c-1)*10) ; i< c*10 ; i++){
//			tempList.add("str"+i);
//		}
//		c++;
//		tempList.add("str"+0);
//		tempList.add("str"+1);
//		tempList.add("str"+2);
//		tempList.add("str"+3);
//		tempList.add("str"+4);
//		tempList.add("str"+5);
//		tempList.add("str"+6);
//		tempList.add("str"+7);
		//tempList.add("str"+8);
		//tempList.add("str"+9);
		
		goldBusiness.insertList(tempList);
	}
}


class OutThread implements Runnable{
	
	private GoldBusiness goldBusiness;
	
	public OutThread(GoldBusiness goldBusiness){
		this.goldBusiness = goldBusiness;
	}

	//@Override
	public void run() {
		goldBusiness.outList();
	}
}

