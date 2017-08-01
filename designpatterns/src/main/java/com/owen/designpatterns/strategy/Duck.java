package com.owen.designpatterns.strategy;

public abstract class Duck {

	//QuackBehavior quackBehavior;

	//FlyBehavior flyBehavior;

	public abstract QuackBehavior getQuackBehavior();

	public abstract FlyBehavior getFlyBehavior();

	//
	// public void setQuackBehavior(QuackBehavior quackBehavior) {
	// this.quackBehavior = quackBehavior;
	// }
	//
	// public void setFlyBehavior(FlyBehavior flyBehavior) {
	// this.flyBehavior = flyBehavior;
	// }

	public void performQuack() {
		getQuackBehavior().quack();
	}

	public void performFly() {
		getFlyBehavior().fly();
	}

	public abstract void display();
}
