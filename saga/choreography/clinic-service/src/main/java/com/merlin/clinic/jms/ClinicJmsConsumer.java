package com.merlin.clinic.jms;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.merlin.clinic.domain.Clinic;
import com.merlin.clinic.dto.PatientDTO;
import com.merlin.clinic.service.ClinicService;
import com.merlin.clinic.util.JsonUtil;

@Component
public class ClinicJmsConsumer {

	private final Logger LOGGER = LoggerFactory.getLogger(ClinicJmsConsumer.class);

	@Autowired
	private ClinicService clinicService;

	@Autowired
	private ClinicJmsProducer clinicJmsProducer;

	@JmsListener(destination = "clinics", selector = "event = 'patient.create.success'")
	public void patientCreateSuccess(String patientJson) {
		try {
			// Business Logic start
//			System.out.println("Received in Clinic Service : patient.create.success " + patientJson);
//			PatientDTO patientDto = JsonUtil.convertJsonToObject(patientJson, PatientDTO.class);
//			Optional<Clinic> clinic = this.clinicService.getClinicById(patientDto.getClinicId());
//			clinic.ifPresent(uClinic -> {
//				uClinic.setNoOfPatients(uClinic.getNoOfPatients() + 1);
//				this.clinicService.saveClinic(uClinic);
//			});
//			patientDto.setStatus("Approved");
//			patientJson = JsonUtil.convertObjectToJson(patientDto);
			// Business Logic end
			clinicJmsProducer.send("patients", "clinic.update.success", patientJson);
		} catch (Exception e) {
			LOGGER.error(" Error while getting clinic object :" + e.getMessage(), e);
			clinicJmsProducer.send("patients", "clinic.update.error", patientJson);
		}
	}

	@JmsListener(destination = "clinics", selector = "event = 'patient.update.error'")
	public void patientUpdateError(String patientJson) {
		// Business Logic start
//		System.out.println("Received in Clinic Service : patient.update.error " + patientJson);
//		PatientDTO patientDto = JsonUtil.convertJsonToObject(patientJson, PatientDTO.class);
//		Optional<Clinic> clinic = this.clinicService.getClinicById(patientDto.getClinicId());
//		clinic.ifPresent(uClinic -> {
//			uClinic.setNoOfPatients(uClinic.getNoOfPatients() - 1);
//			this.clinicService.saveClinic(uClinic);
//		});
		// Business Logic end
		clinicJmsProducer.send("patients", "clinic.update.error", patientJson);
	}

}
