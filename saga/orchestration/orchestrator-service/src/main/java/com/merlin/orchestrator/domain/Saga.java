package com.merlin.orchestrator.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_saga")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Saga {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String sagaName;

	private String currentEventName;

}
