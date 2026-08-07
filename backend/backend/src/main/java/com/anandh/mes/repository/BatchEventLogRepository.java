package com.anandh.mes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.anandh.mes.entity.BatchEventLog;
import com.anandh.mes.enums.BatchEventType;

public interface BatchEventLogRepository
        extends JpaRepository<BatchEventLog, Long> {

    List<BatchEventLog> findByBatchBatchIdOrderByEventTimeAsc(
            Long batchId);

    List<BatchEventLog> findByEventType(
            BatchEventType eventType);

    List<BatchEventLog> findByOperatorNameContainingIgnoreCase(
            String operatorName);

    List<BatchEventLog> findByBatchBatchIdAndEventType(
            Long batchId,
            BatchEventType eventType);

    Page<BatchEventLog> findAll(
            Pageable pageable);

}