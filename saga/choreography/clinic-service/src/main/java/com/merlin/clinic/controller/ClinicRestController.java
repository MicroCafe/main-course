package com.merlin.clinic.controller;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.merlin.clinic.domain.Clinic;
import com.merlin.clinic.service.ClinicService;

@RestController
@RequestMapping("/clinics")
public class ClinicRestController implements Serializable {

	private static final long serialVersionUID = 1L;

	private final ClinicService clinicService;

	public ClinicRestController(ClinicService clinicService) {
		this.clinicService = clinicService;
	}

	@PostMapping
	public void saveClinic(@RequestBody Clinic clinic) {
		this.clinicService.saveClinic(clinic);
	}

	@GetMapping
	public Collection<Clinic> getClinics() {
		return this.clinicService.getClinics();
	}

}
