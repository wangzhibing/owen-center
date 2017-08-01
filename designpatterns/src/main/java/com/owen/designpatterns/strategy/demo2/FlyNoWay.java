package com.owen.designpatterns.strategy.demo2;

public class FlyNoWay implements FlyBehavior {
	
	public void fly() {
		System.out.println("什么事都不做，不会飞");
	}
	
}
