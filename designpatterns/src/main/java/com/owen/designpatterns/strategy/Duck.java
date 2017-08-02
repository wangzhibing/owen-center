package com.owen.designpatterns.strategy;

public abstract class Duck {

	public abstract QuackBehavior getQuackBehavior();

	public abstract FlyBehavior getFlyBehavior();

	public void performQuack() {
		getQuackBehavior().quack();
	}

	public void performFly() {
		getFlyBehavior().fly();
	}

	public abstract void display();
}
