package com.merlin.patient.controller;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.merlin.patient.domain.Patient;
import com.merlin.patient.service.PatientService;

@RestController
@RequestMapping("/patients")
public class PatientRestController implements Serializable {

	private static final long serialVersionUID = 1L;

	private final PatientService patientService;

	public PatientRestController(PatientService patientService) {
		this.patientService = patientService;
	}

	@PostMapping
	public void savePatient(@RequestBody Patient patient) {
		patientService.savePatient(patient);
	}

	@GetMapping
	public Collection<Patient> getPatients() {
		return this.patientService.getPatients();
	}

}
