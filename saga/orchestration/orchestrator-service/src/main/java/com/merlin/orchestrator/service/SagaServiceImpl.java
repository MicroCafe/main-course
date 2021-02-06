package com.merlin.orchestrator.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.merlin.orchestrator.domain.Saga;
import com.merlin.orchestrator.repository.SagaRepository;

@Service
public class SagaServiceImpl implements SagaService {

	@Autowired
	private SagaRepository sagaRepository;

	@Override
	public Optional<Saga> findById(Long id) {
		return this.sagaRepository.findById(id);
	}

	@Transactional
	@Override
	public Saga save(Saga saga) {
		return this.sagaRepository.save(saga);
	}

}
