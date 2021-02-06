package com.merlin.clinic.jms;

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

import com.merlin.clinic.domain.Clinic;
import com.merlin.clinic.model.CreatePatientSagaData;
import com.merlin.clinic.service.ClinicService;
import com.merlin.clinic.util.JsonUtil;

@Component
public class EventConsumer {

	private final Logger LOGGER = LoggerFactory.getLogger(EventConsumer.class);

	@Autowired
	private ClinicService clinicService;

	@Autowired
	private EventPublisher publisher;

	@JmsListener(destination = "clinics", selector = "event = 'UPDATE_CLINIC'")
	public void onUpdateClinicEvent(Message msg) {
		try {

			String body = ((TextMessage) msg).getText();
			Long sagaId = msg.getLongProperty("sagaId");

			LOGGER.info("Received in Clinic-Service : UPDATE_CLINIC" + body);

			CreatePatientSagaData event = JsonUtil.fromJson(body, CreatePatientSagaData.class);

			try {
				Optional<Clinic> clinic = this.clinicService.getClinicById(event.getClinicId());
				clinic.ifPresent(uClinic -> {
					uClinic.setNoOfPatients(uClinic.getNoOfPatients() + 1);
					this.clinicService.saveClinic(uClinic);
				});

				event.setStatus("Approved");

				this.publisher.send("orchestrator", "CREATE_PATIENT_SAGA", "CLINIC_UPDATED", JsonUtil.toJson(event),
						sagaId);
			} catch (DataAccessException e) {
				LOGGER.info("Error while updating clinic object in clinic service : " + e.getMessage(), e);
				this.publisher.send("clinics", "CREATE_PATIENT_SAGA", "UPDATE_CLINIC_ERROR", JsonUtil.toJson(event),
						sagaId);
			}

		} catch (JMSException e) {
			LOGGER.info("Error while processing message in clinic service : " + e.getMessage(), e);
		}
	}

	@JmsListener(destination = "clinics", selector = "event = 'ROLLBACK_CLINIC_UPDATED'")
	public void onRollbackClinicUpdatedEvent(Message msg) {
		try {

			String body = ((TextMessage) msg).getText();
			Long sagaId = msg.getLongProperty("sagaId");

			LOGGER.info("Received in Clinic-Service : ROLLBACK_CLINIC_UPDATED" + body);

			CreatePatientSagaData event = JsonUtil.fromJson(body, CreatePatientSagaData.class);

			Optional<Clinic> clinic = this.clinicService.getClinicById(event.getClinicId());
			clinic.ifPresent(uClinic -> {
				uClinic.setNoOfPatients(uClinic.getNoOfPatients() - 1);
				this.clinicService.saveClinic(uClinic);
			});

			this.publisher.send("orchestrator", "CREATE_PATIENT_SAGA", "UPDATE_CLINIC_ERROR", JsonUtil.toJson(event),
					sagaId);

		} catch (JMSException e) {
			LOGGER.info("Error while processing message in clinic service : " + e.getMessage(), e);
		}
	}

}
