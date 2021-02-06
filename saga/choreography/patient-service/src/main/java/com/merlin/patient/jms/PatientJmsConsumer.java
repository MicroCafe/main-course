package com.merlin.patient.jms;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.merlin.patient.domain.Patient;
import com.merlin.patient.dto.PatientDTO;
import com.merlin.patient.service.PatientService;
import com.merlin.patient.util.JsonUtil;

@Component
public class PatientJmsConsumer {

	private final Logger LOGGER = LoggerFactory.getLogger(PatientJmsConsumer.class);

	private final PatientService patientService;
	private final PatientJmsProducer patientJmsProducer;

	public PatientJmsConsumer(PatientService patientService, PatientJmsProducer patientJmsProducer) {
		this.patientService = patientService;
		this.patientJmsProducer = patientJmsProducer;
	}

	@JmsListener(destination = "patients", selector = "event = 'clinic.update.success'")
	public void clinicUpdateSuccess(String patientJson) {
		// Business Logic start
		try {
			System.out.println("Received in Patient-Service : clinic.update.success " + patientJson);
			PatientDTO patientDTO = JsonUtil.convertJsonToObject(patientJson, PatientDTO.class);
			Optional<Patient> patient = this.patientService.findById(patientDTO.getId());
			patient.ifPresent(uPatient -> {
				uPatient.setStatus(patientDTO.getStatus());
				patientService.updatePatient(uPatient);
			});
		// Business Logic end
		} catch (Exception e) {
			LOGGER.error(" Error in Patient Service :" + e.getMessage(), e);
			patientJmsProducer.send("clinics", "patient.update.error", patientJson);
		}
	}

	@JmsListener(destination = "patients", selector = "event = 'clinic.update.error'")
	public void clinicUpdateError(String patientJson) {
		// Business Logic start
		System.out.println("Received in Patient-Service : clinic.update.error " + patientJson);
		PatientDTO patientDTO = JsonUtil.convertJsonToObject(patientJson, PatientDTO.class);
		this.patientService.deleteById(patientDTO.getId());
		// Business Logic end
	}

}
