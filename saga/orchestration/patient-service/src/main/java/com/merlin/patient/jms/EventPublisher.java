package com.merlin.patient.jms;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventPublisher {

	private final JmsTemplate jmsTemplate;

	public EventPublisher(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	
	public void send(String dest, String sagaName, String eventName, String msg) {
		this.jmsTemplate.convertAndSend(dest, msg, message -> {
			message.setStringProperty("saga", sagaName);
			message.setStringProperty("event", eventName);
			return message;
		});
	}
	
	public void send(String dest, String sagaName, String eventName, String msg, Long sagaId) {
		this.jmsTemplate.convertAndSend(dest, msg, message -> {
			message.setStringProperty("saga", sagaName);
			message.setLongProperty("sagaId", sagaId);
			message.setStringProperty("event", eventName);
			return message;
		});
	}

}
