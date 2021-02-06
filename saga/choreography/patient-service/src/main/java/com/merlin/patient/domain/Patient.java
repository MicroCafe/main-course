package com.merlin.patient.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_patient")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long clinicId;
	private String firstName;
	private String lastName;
	private int age;
	private String gender;
	private String address;
	private String status;

}
