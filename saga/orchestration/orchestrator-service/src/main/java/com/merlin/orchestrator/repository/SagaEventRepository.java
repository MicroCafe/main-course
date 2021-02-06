//package com.merlin.orchestrator.repository;
//
//import java.util.Optional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import com.merlin.orchestrator.domain.SagaEvent;
//
//public interface SagaEventRepository extends JpaRepository<SagaEvent, Long> {
//
//	Optional<SagaEvent> findBySagaIdAndEventName(Long sagaId, String eventName);
//
//	@Query(value = "SELECT * FROM tbl_saga_event se WHERE se.saga_id = :sagaId "
//			+ " AND se.step > :step AND se.compensating = 0 ORDER BY se.step ASC LIMIT 1", nativeQuery = true)
//	Optional<SagaEvent> findBySagaId(@Param("sagaId") Long sagaId, @Param("step") Integer step);
//
//	@Query(value = "SELECT * FROM tbl_saga_event se WHERE se.saga_id = :sagaId "
//			+ " AND se.step < :step AND se.compensating = 1 ORDER BY se.step DESC LIMIT 1", nativeQuery = true)
//	Optional<SagaEvent> findBySagaIdCompensations(@Param("sagaId") Long sagaId, @Param("step") Integer step);
//
//}
