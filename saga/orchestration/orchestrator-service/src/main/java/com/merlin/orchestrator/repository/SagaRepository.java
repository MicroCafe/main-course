package com.merlin.orchestrator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.merlin.orchestrator.domain.Saga;

public interface SagaRepository extends JpaRepository<Saga, Long> {

}
