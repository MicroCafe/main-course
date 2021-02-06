package com.merlin.clinic.service;

import java.util.Collection;
import java.util.Optional;

import com.merlin.clinic.domain.Clinic;

public interface ClinicService {

	Clinic saveClinic(Clinic clinic);

	Collection<Clinic> getClinics();

	Optional<Clinic> getClinicById(Long clinicId);

}
