package com.owen.designpatterns.creativity.factory.factorymethod.commonfactory;

import com.owen.designpatterns.creativity.factory.Sender;

/**
 * 设计模式－工厂模式(普通工厂模式) 就是建立一个工厂类，对实现了同一接口的一些类进行实例的创建
 * 
 * @author owen
 *
 */
public class CommonFactoryTest {
	public static void main(String[] args) {
		CommonSendFactory factory = new CommonSendFactory();
		Sender sender = factory.produce("sms");
		sender.send();
	}
}
