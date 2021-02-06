package com.merlin.patient.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.merlin.patient.domain.Patient;
import com.merlin.patient.dto.PatientDTO;
import com.merlin.patient.jms.PatientJmsProducer;
import com.merlin.patient.repository.PatientRepository;
import com.merlin.patient.util.JsonUtil;

@Service
public class PatientServiceImpl implements PatientService {

	private final PatientRepository patientRepository;
	private final PatientJmsProducer patientJmsProducer;

	public PatientServiceImpl(PatientRepository patientRepository, PatientJmsProducer patientJmsProducer) {
		this.patientRepository = patientRepository;
		this.patientJmsProducer = patientJmsProducer;
	}

	@Override
	@Transactional
	public void savePatient(Patient patient) {
		this.patientRepository.save(patient);
		PatientDTO patientDTO = new PatientDTO(patient.getId(), patient.getClinicId());
		patientJmsProducer.send("clinics","patient.create.success", JsonUtil.convertObjectToJson(patientDTO));
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
