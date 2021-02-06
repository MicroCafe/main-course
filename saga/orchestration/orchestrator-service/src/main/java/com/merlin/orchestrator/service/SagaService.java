package com.merlin.orchestrator.service;

import java.util.Optional;

import com.merlin.orchestrator.domain.Saga;

public interface SagaService {
	
	Optional<Saga> findById(Long id);

	Saga save(Saga saga);
}
