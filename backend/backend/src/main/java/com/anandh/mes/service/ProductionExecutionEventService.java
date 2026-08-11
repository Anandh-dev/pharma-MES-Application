package com.anandh.mes.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;

import com.anandh.mes.dto.ProductionExecutionEventDTO;
import com.anandh.mes.enums.ProductionEventType;

public interface ProductionExecutionEventService {

    // ==========================================================
    // CRUD
    // ==========================================================

    ProductionExecutionEventDTO createEvent(
            ProductionExecutionEventDTO dto);

    List<ProductionExecutionEventDTO> getAllEvents();

    ProductionExecutionEventDTO getEventById(
            Long id);

    void deleteEvent(Long id);

    // ==========================================================
    // SEARCH BY WORK ORDER
    // ==========================================================

    List<ProductionExecutionEventDTO>
    getByWorkOrder(Long workOrderId);

    // ==========================================================
    // SEARCH BY BATCH
    // ==========================================================

    List<ProductionExecutionEventDTO>
    getByBatch(Long batchId);

    // ==========================================================
    // SEARCH BY ASSIGNMENT
    // ==========================================================

    List<ProductionExecutionEventDTO>
    getByAssignment(Long assignmentId);

    // ==========================================================
    // SEARCH BY EVENT TYPE
    // ==========================================================

    List<ProductionExecutionEventDTO>
    getByEventType(
            ProductionEventType eventType);

    // ==========================================================
    // SEARCH BY OPERATOR
    // ==========================================================

    List<ProductionExecutionEventDTO>
    getByOperator(String operatorName);

    // ==========================================================
    // SEARCH BY DATE RANGE
    // ==========================================================

    List<ProductionExecutionEventDTO>
    getByEventTimeBetween(
            LocalDateTime start,
            LocalDateTime end);

    // ==========================================================
    // WORK ORDER + EVENT TYPE
    // ==========================================================

    List<ProductionExecutionEventDTO>
    getByWorkOrderAndEventType(
            Long workOrderId,
            ProductionEventType eventType);

    // ==========================================================
    // WORK ORDER + DATE RANGE
    // ==========================================================

    List<ProductionExecutionEventDTO>
    getByWorkOrderAndEventTimeBetween(
            Long workOrderId,
            LocalDateTime start,
            LocalDateTime end);

    // ==========================================================
    // PAGINATION
    // ==========================================================

    Page<ProductionExecutionEventDTO>
    getEventPage(
            int page,
            int size,
            String sortBy);
    
    ProductionExecutionEventDTO recordEvent(
            Long workOrderId,
            ProductionEventType eventType,
            String operatorName,
            Long batchId,
            Long assignmentId,
            String remarks);
}