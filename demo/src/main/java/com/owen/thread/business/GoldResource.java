package com.owen.thread.business;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class GoldResource {
	
	private List<String> priceList =  new ArrayList<String>();
	
	
	private Lock lock = new ReentrantLock();
	Condition inCondition = lock.newCondition();
	Condition outCondition = lock.newCondition();
	
	
	public void insertList(List<String> tempList){
		try {
			lock.lock();
			while(priceList != null && priceList.size() > 0){
				inCondition.await();
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
			outCondition.signal();
			
		} catch (Exception e) {
			
		} finally{
			lock.unlock();
		}
	}
	
	public void outList(){
		
		try {
			lock.lock();
			while(priceList == null || priceList.size() == 0){
				outCondition.await();
			}
			
			System.out.println(Thread.currentThread().getName()+"清空前：priceList大小："+priceList.size());
			priceList.clear();
			System.out.println(Thread.currentThread().getName()+"清空后:priceList大小："+priceList.size());
			
			inCondition.signal();
			
		} catch (Exception e) {
		}
		finally{
			lock.unlock();
		}
	}
}


