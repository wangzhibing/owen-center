package com.owen.jvm;

/***
 * VM Args:-Xss2M
 * 
 * @author owen
 *
 */
public class JavaVMStackOOM {

	private void dontStop(){
		while(true){
			
		}
	}
	
	public void stackLeakByThread(){
		while(true){
			//Thread thread = new 
		}
		
		
	}
	
	public static void main(String[] args) {
//		JavaVMStackOOM oom = new JavaVMStackOOM();
//		try {
//			oom.stackLeak();
//		} catch (Exception e) {
//			System.out.println("stack length:" + oom.stackLength);
//			throw e;
//		}
	}
}
