package com.merlin.orchestrator.model;

import java.util.Map;

import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SagaConfig {

	private String name;

	@Singular
	@Transient
	private Map<String, SagaEventConfig> events;

}
