package com.owen.designpatterns.strategy.demo2;

public class QuackVoice implements QuackBehavior {

	public void quack() {
		System.out.println("大声的叫出来");
	}

}
