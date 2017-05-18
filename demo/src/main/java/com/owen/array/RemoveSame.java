package com.owen.array;

import java.util.ArrayList;
import java.util.List;


public class RemoveSame {

	/**
	 * 
	 * 方法简述：
	 * <p>方法详细描述<br>
	 * @Author: owen
	 * @Since: 2014-7-30
	 * @param args
	 */
	public static void main(String[] args) {
		
		Demo d1 = new Demo("1001");
		Demo d2 = new Demo("1001");
		Demo d3 = new Demo("1002");
		Demo d4 = new Demo("1001");
		Demo d5 = new Demo("1001");
		Demo d6 = new Demo("1002");
		Demo d7 = new Demo("1001");
		Demo d8 = new Demo("1001");
		Demo d9 = new Demo("1004");
		Demo d10 = new Demo("1001");
		
		List<Demo> list = new ArrayList<Demo>();
		list.add(d1);
		list.add(d2);
		list.add(d3);
		list.add(d4);
		list.add(d5);
		list.add(d6);
		list.add(d7);
		list.add(d8);
		list.add(d9);
		list.add(d10);
	}
	
	public static void remove2(List<Demo> list){
		List<Demo> list1 = new ArrayList<Demo>(list);
		for(int i=0; i<list.size() ; i++){
			Demo demo = list.get(i);
			for(int j= list1.size() - 1; j>0 ; j--){
				Demo demo1 = list1.get(j);
				
			}
		}
	}
	
	
	public static void remove1(List<Demo> list){
		int size = list.size();
		int c = 0;
		for(int i = 0;i < size;i++ ) {
			System.out.println("一循坏：size:"+size);
			Demo demoTemp = list.get(i);
			String id = demoTemp.getId();
			for(int j = size - 1;j > i;j-- ) {//重复项累计并且剔除重复项
				if(id.equals(list.get(j).getId())) {
					demoTemp.setNum(demoTemp.getNum() + list.get(j).getNum());
					demoTemp.setCount(demoTemp.getCount() + 1);
					list.set(i, demoTemp);
					list.remove(j);
					size = list.size();
					System.out.println("二循坏：size:"+size);
				}
				c++;
			}
		}
		
		System.out.println("总共运行"+c+"次");
		System.out.println("合并后还有"+list.size()+"条");
		for(Demo demo :list){
			System.out.println("id"+demo.getId()+",num:"+demo.getNum()+",count:"+demo.getCount());
		}
	}
	
}


class Demo{
	private String id;
	
	private Integer num;
	
	private Integer count;

	public Demo(String id){
		this.id = id;
		this.num = 1;
		this.count = 1;
	}
	
	public Demo(String id,Integer num){
		this.id = id;
		this.num = num;
		this.count = 1;
	}
	
	public String getId() {
		return id;
	}

	
	public void setId(String id) {
		this.id = id;
	}

	
	public Integer getNum() {
		return num;
	}

	
	public void setNum(Integer num) {
		this.num = num;
	}

	
	public Integer getCount() {
		return count;
	}

	
	public void setCount(Integer count) {
		this.count = count;
	}
	
	public String toString(){
		return "编码："+id+",数量："+num;
	}
}
