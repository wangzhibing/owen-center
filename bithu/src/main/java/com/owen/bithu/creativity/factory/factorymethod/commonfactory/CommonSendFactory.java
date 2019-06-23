package com.owen.designpatterns.creativity.factory.factorymethod.commonfactory;

import com.owen.designpatterns.creativity.factory.MailSender;
import com.owen.designpatterns.creativity.factory.Sender;
import com.owen.designpatterns.creativity.factory.SmsSender;

public class CommonSendFactory {

	public Sender produce(String type) {
		if ("mail".equals(type)) {
			return new MailSender();
		} else if ("sms".equals(type)) {
			return new SmsSender();
		} else {
			System.out.println("请输入正确的类型");
			return null;
		}
	}
}
