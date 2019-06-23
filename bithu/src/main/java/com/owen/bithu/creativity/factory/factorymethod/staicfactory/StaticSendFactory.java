package com.owen.designpatterns.creativity.factory.factorymethod.staicfactory;

import com.owen.designpatterns.creativity.factory.MailSender;
import com.owen.designpatterns.creativity.factory.Sender;
import com.owen.designpatterns.creativity.factory.SmsSender;

public class StaticSendFactory {

	public static Sender produceMail() {
		return new MailSender();
	}

	public static Sender produceSms() {
		return new SmsSender();
	}
}
