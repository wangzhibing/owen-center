package com.owen.designpatterns.demo.demo2;

public class MallardDuck extends Duck {

	public MallardDuck() {
	}

	private FlyWithWings flyWithWings;

	private QuackVoice quackVoice;

	@Override
	public void display() {
		System.out.println("显示出来");
	}

	@Override
	public QuackBehavior getQuackBehavior() {
		return quackVoice;
	}

	@Override
	public FlyBehavior getFlyBehavior() {
		return flyWithWings;
	}
	
	
	public static void main(String[] args) {
		Duck s = new MallardDuck();
		s.performFly();
	}
}
