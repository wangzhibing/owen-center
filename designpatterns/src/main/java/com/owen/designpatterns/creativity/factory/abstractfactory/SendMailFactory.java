package com.owen.designpatterns.creativity.factory.abstractfactory;

import com.owen.designpatterns.creativity.factory.MailSender;
import com.owen.designpatterns.creativity.factory.Sender;

public class SendMailFactory implements Provider {

	@Override
	public Sender produce() {
		return new MailSender();
	}

}
