package com.owen.designpatterns.creativity.factory.factorymethod.staicfactory;

import com.owen.designpatterns.creativity.factory.Sender;

/**
 * 设计模式－工厂模式(s静态工厂方法模式) 将上面的多个工厂方法模式里的方法置为静态的，不需要创建实例，直接调用即可
 * 
 * @author owen
 *
 */
public class StaticFactoryTest {
	public static void main(String[] args) {
		Sender sender = StaticSendFactory.produceMail();
		sender.send();
	}
}
