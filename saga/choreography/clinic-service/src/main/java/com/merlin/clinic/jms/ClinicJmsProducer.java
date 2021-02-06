package com.merlin.clinic.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class ClinicJmsProducer {

	@Autowired
	private JmsTemplate jmsTemplate;

	public void send(String dest, String event, String msg) {
		this.jmsTemplate.convertAndSend(dest, msg, message -> {
			message.setStringProperty("event", event);
			return message;
		});
	}
	
}
