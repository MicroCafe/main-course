package com.merlin.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.merlin.patient.domain.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {

}
