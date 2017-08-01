package com.owen.designpatterns.strategy;

public class QuackVoice implements QuackBehavior {

	public void quack() {
		System.out.println("大声的叫出来");
	}

}
