package com.owen.designpatterns.strategy.demo1;

/**
 * 鸭子抽象超类
 */
public abstract class Duck {
	
	public void quack(){
		System.out.println("鸭子呱呱叫");
	}
	
	
	public void swim(){
		System.out.println("鸭子游泳");
	}
	
	/**
	 * 鸭子外观
	 */
	public abstract void display();

}

/**
 * 绿头鸭子
 */
class MallardDuck extends Duck {

	@Override
	public void display() {
		System.out.println("绿头鸭子");
	}
}

/**
 * 红头鸭子
 */
class RedHeadDuck extends Duck {

	@Override
	public void display() {
		System.out.println("红头鸭子");
	}
	
}
