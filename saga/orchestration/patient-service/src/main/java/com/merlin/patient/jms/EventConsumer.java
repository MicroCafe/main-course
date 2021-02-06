package com.merlin.patient.jms;

import java.util.Optional;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.merlin.patient.domain.Patient;
import com.merlin.patient.model.CreatePatientSagaData;
import com.merlin.patient.service.PatientService;
import com.merlin.patient.util.JsonUtil;

@Component
public class EventConsumer {

	private final Logger LOGGER = LoggerFactory.getLogger(EventConsumer.class);

	@Autowired
	private PatientService patientService;
	
	@Autowired
	private EventPublisher publisher;


	@JmsListener(destination = "patients", selector = "event = 'UPDATE_PATIENT'")
	public void onUpdatePatientEvent(Message msg) {
		try {

			String body = ((TextMessage) msg).getText();
			Long sagaId = msg.getLongProperty("sagaId");

			LOGGER.info("Received in Patient-Service : UPDATE_PATIENT " + body);

			CreatePatientSagaData event = JsonUtil.fromJson(body, CreatePatientSagaData.class);

			try {
				Optional<Patient> patient = this.patientService.findById(event.getPatientId());
				patient.ifPresent(uPatient -> {
					uPatient.setStatus(event.getStatus());
					patientService.updatePatient(uPatient);
				});

			} catch (DataAccessException e) {
				LOGGER.info("Error while updating patient status in patient service : " + e.getMessage(), e);
				this.publisher.send("patients", "CREATE_PATIENT_SAGA", "UPDATE_PATIENT_ERROR", body, sagaId);
			}

		} catch (JMSException e) {
			LOGGER.info("Error while processing message in patient service : " + e.getMessage(), e);
		}

	}

	@JmsListener(destination = "patients", selector = "event = 'ROLLBACK_PATIENT_CREATED'")
	public void onRollbackPatientCreatedEvent(Message msg) {
		try {

			String body = ((TextMessage) msg).getText();
			LOGGER.info("Received in Patient-Service : ROLLBACK_PATIENT_CREATED" + body);

			CreatePatientSagaData event = JsonUtil.fromJson(body, CreatePatientSagaData.class);
			this.patientService.deleteById(event.getPatientId());

		} catch (JMSException e) {
			LOGGER.info("Error while processing message in patient service : " + e.getMessage(), e);
		}

	}

}
