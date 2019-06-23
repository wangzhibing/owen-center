package com.owen.designpatterns.creativity.factory.abstractfactory;

import com.owen.designpatterns.creativity.factory.Sender;

public class AbstractFactory {
	public static void main(String[] args) {
		Provider provider = new SendMailFactory();
		Sender sender = provider.produce();
		sender.send();
	}
}
