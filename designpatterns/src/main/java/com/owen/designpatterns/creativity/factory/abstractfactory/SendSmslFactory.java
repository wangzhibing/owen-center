package com.owen.designpatterns.creativity.factory.abstractfactory;

import com.owen.designpatterns.creativity.factory.Sender;
import com.owen.designpatterns.creativity.factory.SmsSender;

public class SendSmslFactory implements Provider {

	@Override
	public Sender produce() {
		return new SmsSender();
	}

}
