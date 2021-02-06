package com.merlin.clinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.merlin.clinic.domain.Clinic;
import com.merlin.clinic.repository.ClinicRepository;

@Service
public class ClinicServiceImpl implements ClinicService {

	private final ClinicRepository clinicRepository;

	public ClinicServiceImpl(ClinicRepository clinicRepository) {
		this.clinicRepository = clinicRepository;
	}

	@Transactional
	@Override
	public void saveClinic(Clinic clinic) {
		this.clinicRepository.save(clinic);
	}

	@Transactional(readOnly = true)
	@Override
	public Collection<Clinic> getClinics() {
		return this.clinicRepository.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Clinic> getClinicById(Long clinicId) {
		return this.clinicRepository.findById(clinicId);
	}

}
