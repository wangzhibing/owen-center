package com.owen.designpatterns.creativity.factory.factorymethod.multifactory;

import com.owen.designpatterns.creativity.factory.MailSender;
import com.owen.designpatterns.creativity.factory.Sender;
import com.owen.designpatterns.creativity.factory.SmsSender;

public class MultiSendFactory {

	public Sender produceMail() {
		return new MailSender();
	}

	public Sender produceSms() {
		return new SmsSender();
	}
}
