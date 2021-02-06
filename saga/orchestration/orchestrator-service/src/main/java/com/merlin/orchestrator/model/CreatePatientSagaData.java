package com.merlin.orchestrator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePatientSagaData {

	private Long patientId;
	private Long clinicId;
	private String status;

}
