package com.merlin.patient.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.merlin.patient.domain.Patient;
import com.merlin.patient.jms.EventPublisher;
import com.merlin.patient.model.CreatePatientSagaData;
import com.merlin.patient.repository.PatientRepository;
import com.merlin.patient.util.JsonUtil;

@Service
public class PatientServiceImpl implements PatientService {

	private final PatientRepository patientRepository;
	private final EventPublisher publisher;

	public PatientServiceImpl(PatientRepository patientRepository, EventPublisher publisher) {
		this.patientRepository = patientRepository;
		this.publisher = publisher;
	}

	@Override
	@Transactional
	public Patient savePatient(Patient patient) {
		this.patientRepository.save(patient);

		CreatePatientSagaData msg = new CreatePatientSagaData(patient.getId(), patient.getClinicId(), null);
		this.publisher.send("orchestrator", "CREATE_PATIENT_SAGA", "PATIENT_CREATED", JsonUtil.toJson(msg));

		return patient;
	}

	@Override
	@Transactional
	public void updatePatient(Patient patient) {
		this.patientRepository.save(patient);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Patient> findById(Long patientId) {
		return this.patientRepository.findById(patientId);
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Patient> getPatients() {
		return this.patientRepository.findAll();
	}

	@Transactional
	@Override
	public void deleteById(Long patientId) {
		this.patientRepository.deleteById(patientId);
	}

}
