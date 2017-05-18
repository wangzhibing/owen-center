package com.owen.jvm;

/***
 * VM Args:-Xss128k
 * 
 * @author owen
 *
 */
public class JavaVMStackSOF {
	private int stackLength = 1;

	public void stackLeak() {
		stackLength++;
		stackLeak();
	}

	public static void main(String[] args) {
		JavaVMStackSOF oom = new JavaVMStackSOF();
		try {
			oom.stackLeak();
		} catch (Exception e) {
			System.out.println("stack length:" + oom.stackLength);
		}
	}
}
