package com.merlin.clinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.merlin.clinic.domain.Clinic;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {

}
