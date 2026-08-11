package com.anandh.mes.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.anandh.mes.entity.ProductionExecutionEvent;
import com.anandh.mes.enums.ProductionEventType;

public interface ProductionExecutionEventRepository
        extends JpaRepository<ProductionExecutionEvent, Long> {

    // ==========================================================
    // SEARCH BY WORK ORDER
    // ==========================================================

    List<ProductionExecutionEvent>
    findByWorkOrderWorkOrderId(Long workOrderId);

    // ==========================================================
    // SEARCH BY BATCH
    // ==========================================================

    List<ProductionExecutionEvent>
    findByBatchBatchId(Long batchId);

    // ==========================================================
    // SEARCH BY ASSIGNMENT
    // ==========================================================

    List<ProductionExecutionEvent>
    findByAssignmentAssignmentId(Long assignmentId);

    // ==========================================================
    // SEARCH BY EVENT TYPE
    // ==========================================================

    List<ProductionExecutionEvent>
    findByEventType(ProductionEventType eventType);

    // ==========================================================
    // SEARCH BY OPERATOR
    // ==========================================================

    List<ProductionExecutionEvent>
    findByOperatorNameContainingIgnoreCase(
            String operatorName);

    // ==========================================================
    // SEARCH BY EVENT TIME
    // ==========================================================

    List<ProductionExecutionEvent>
    findByEventTimeBetween(
            LocalDateTime start,
            LocalDateTime end);

    // ==========================================================
    // WORK ORDER + EVENT TYPE
    // ==========================================================

    List<ProductionExecutionEvent>
    findByWorkOrderWorkOrderIdAndEventType(
            Long workOrderId,
            ProductionEventType eventType);

    // ==========================================================
    // WORK ORDER + EVENT TIME
    // ==========================================================

    List<ProductionExecutionEvent>
    findByWorkOrderWorkOrderIdAndEventTimeBetween(
            Long workOrderId,
            LocalDateTime start,
            LocalDateTime end);

    // ==========================================================
    // PAGINATION
    // ==========================================================

    Page<ProductionExecutionEvent> findAll(
            Pageable pageable);

}