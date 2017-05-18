package com.owen.thread.business;

import java.util.ArrayList;
import java.util.List;


public class GoldBusiness {
	
	private List<String> priceList =  new ArrayList<String>();
	
	//private Object Lock = new Object();  
	
	
	public void insertList(List<String> tempList){
		//while(true){}
		synchronized (priceList) {
			while(priceList != null && priceList.size() > 0){
				try {
					priceList.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			System.out.println(Thread.currentThread().getName()+"装载前：priceList大小："+priceList.size());
			for(int i=0;i<10;i++){
				priceList.add("str"+i);
			}
			System.out.println(Thread.currentThread().getName()+"装载后：priceList大小："+priceList.size());

			
//			if(tempList != null && tempList.size() > 0){
//				System.out.println(Thread.currentThread().getName()+"装载前：priceList大小："+priceList.size());
//				priceList.addAll(tempList);
//				System.out.println(Thread.currentThread().getName()+"装载后：priceList大小："+priceList.size());
//			}
			
			priceList.notifyAll();
		}
	}
	
	public void outList(){
		//while(true){}
		synchronized (priceList) {
			while(priceList == null || priceList.size() == 0){
				try {
					priceList.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			System.out.println(Thread.currentThread().getName()+"清空前：priceList大小："+priceList.size());
			priceList.clear();
			System.out.println(Thread.currentThread().getName()+"清空后:priceList大小："+priceList.size());
			
			priceList.notifyAll();
		}
	}
}


