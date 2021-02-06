package com.merlin.patient.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class PatientJmsProducer {

	@Autowired
	private JmsTemplate jmsTemplate;

	public void send(String dest, String event, String msg) {
		this.jmsTemplate.convertAndSend(dest, msg, message -> {
			message.setStringProperty("event", event);
			return message;
		});
	}

}
