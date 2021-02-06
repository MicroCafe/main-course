package com.merlin.patient.service;

import java.util.Collection;
import java.util.Optional;

import com.merlin.patient.domain.Patient;

public interface PatientService {

	void savePatient(Patient patient);

	Optional<Patient> findById(Long patientId);

	Collection<Patient> getPatients();

	void updatePatient(Patient patient);

	void deleteById(Long patientId);

}
