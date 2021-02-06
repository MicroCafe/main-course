package com.merlin.clinic;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import com.merlin.clinic.domain.Clinic;
import com.merlin.clinic.service.ClinicService;

@SpringBootApplication
@ActiveProfiles("dev")
public class ClinicServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClinicServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(ClinicService clinicService) {
		return args -> {
			Clinic clinic = new Clinic();
			clinic.setId(1L);
			clinic.setName("Clinic-1");
			clinicService.saveClinic(clinic);
		};
	}

}
