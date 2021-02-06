package com.merlin.orchestrator.jms;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.merlin.orchestrator.domain.Saga;
import com.merlin.orchestrator.model.SagaConfig;
import com.merlin.orchestrator.model.SagaEventConfig;
import com.merlin.orchestrator.service.SagaService;

@Component
public class EventConsumer {

	private final Logger LOGGER = LoggerFactory.getLogger(EventConsumer.class);

	@Autowired
	private SagaService sagaService;

	@Autowired
	private EventPublisher publisher;

	private static Map<String, SagaConfig> SAGA_CONFIG_REG = new HashMap<>();

	static {
		SagaConfig patientSagaConfig = SagaConfig.builder().name("CREATE_PATIENT_SAGA")//
				.event("PATIENT_CREATED", new SagaEventConfig("clinics", "UPDATE_CLINIC"))//
				.event("CLINIC_UPDATED", new SagaEventConfig("patients", "UPDATE_PATIENT"))//
				.event("UPDATE_CLINIC_ERROR", new SagaEventConfig("patients", "ROLLBACK_PATIENT_CREATED"))//
				.event("UPDATE_PATIENT_ERROR", new SagaEventConfig("clinics", "ROLLBACK_CLINIC_UPDATED"))//
				.build();

		addSagaConfig(patientSagaConfig);
	}

	private static void addSagaConfig(SagaConfig sagaConfig) {
		SAGA_CONFIG_REG.put(sagaConfig.getName(), sagaConfig);
	}

	private static SagaConfig getSagaConfig(String sagaName) {
		return SAGA_CONFIG_REG.get(sagaName);
	}

	@JmsListener(destination = "orchestrator")
	public void onEvent(Message msg) {
		try {

			String sagaName = msg.getStringProperty("saga");
			String eventName = msg.getStringProperty("event");

			Long sagaId;
			if (msg.propertyExists("sagaId")) {
				sagaId = msg.getLongProperty("sagaId");
			} else {
				Saga saga = Saga.builder().sagaName(sagaName).currentEventName(eventName).build();
				sagaId = this.sagaService.save(saga).getId();
			}

			String body = ((TextMessage) msg).getText();
			SagaEventConfig sagaEvent = getSagaConfig(sagaName).getEvents().get(eventName);
			this.publisher.send(sagaEvent.getDestination(), sagaName, sagaEvent.getDestinationEvent(), body, sagaId);
			
			Saga saga = this.sagaService.findById(sagaId).get();
			saga.setCurrentEventName(eventName);
			this.sagaService.save(saga);
			
		} catch (JMSException e) {
			LOGGER.error("Error while creating saga : " + e.getMessage(), e);
		}
	}

}