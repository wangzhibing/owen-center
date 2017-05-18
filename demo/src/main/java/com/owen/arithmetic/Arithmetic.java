package com.owen.arithmetic;

/**
 * 算法篇--斐波那契数列
 * @Title: Arithmetic
 * @Description: XXXXX
 * @Author: owen
 * @Since: 2014-7-18
 * @Version: 1.1.0
 */
public class Arithmetic {
	
	
	
	private static double countPercent(double rate) {
        //return Math.round(rate * 100);
        
        return Math.round((rate * 100) * 100 /100.0);
    }
	
	public static void main(String[] args) {
		System.out.println(Arithmetic.countPercent(0.9876543));
		//System.out.println(Math.round(0.9 * 100));
		//System.out.println(Math.round(12.635));
	}

	/**
	 * 
	 * 方法简述：
	 * <p>方法详细描述<br>
	 * @Author: owen
	 * @Since: 2014-7-18
	 * @param args
	 */
//	public static void main(String[] args) {
//		Arithmetic.fibonacci(10);
//	}
//	
	
	/**
	 * 方法简述：fibonacci
	 * 斐波那契数列1,1,2,3,5,8,13,21,34,55.....
	 * @Author: owen
	 * @Since: 2014-7-18
	 * @param i
	 */
	public static void fibonacci(int len){
		 int[] result = new int[len];
	     result[0] = 0;
	     result[1] = 1;
	     for(int i=2; i<len; i++) {
	         result[i] = result[i-1] + result[i-2];
	     }
	     for(int i=0;i<len;i++){
	        System.out.println(result[i]);
	     }
	}
	
}


