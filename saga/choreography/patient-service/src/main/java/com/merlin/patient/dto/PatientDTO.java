package com.merlin.patient.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long clinicId;
	private String status;

	public PatientDTO(Long id, Long clinicId) {
		this.id = id;
		this.clinicId = clinicId;
	}

}
