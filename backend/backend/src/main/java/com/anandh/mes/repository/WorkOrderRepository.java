package com.anandh.mes.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.anandh.mes.entity.WorkOrder;
import com.anandh.mes.enums.WorkOrderStatus;

public interface WorkOrderRepository
        extends JpaRepository<WorkOrder, Long> {

    // ==========================================================
    // SEARCH BY WORK ORDER NUMBER
    // ==========================================================

    Optional<WorkOrder> findByWorkOrderNumber(
            String workOrderNumber);

    // ==========================================================
    // SEARCH BY STATUS
    // ==========================================================

    List<WorkOrder> findByStatus(
            WorkOrderStatus status);

    // ==========================================================
    // SEARCH BY PRODUCTION SCHEDULE
    // ==========================================================

    List<WorkOrder> findByProductionScheduleProductionScheduleId(
            Long productionScheduleId);

    // ==========================================================
    // SEARCH BY BATCH
    // ==========================================================

    List<WorkOrder> findByBatchBatchId(
            Long batchId);

    // ==========================================================
    // SEARCH BY PRODUCT
    // ==========================================================

    List<WorkOrder> findByProductNameContainingIgnoreCase(
            String productName);

    // ==========================================================
    // SEARCH BY PRIORITY
    // ==========================================================

    List<WorkOrder> findByPriority(
            Integer priority);

    // ==========================================================
    // SEARCH BY PLANNED START
    // ==========================================================

    List<WorkOrder> findByPlannedStartBetween(
            LocalDateTime start,
            LocalDateTime end);

    // ==========================================================
    // PRODUCT + STATUS
    // ==========================================================

    List<WorkOrder>
    findByProductNameContainingIgnoreCaseAndStatus(
            String productName,
            WorkOrderStatus status);

    // ==========================================================
    // PRIORITY + STATUS
    // ==========================================================

    List<WorkOrder> findByPriorityAndStatus(
            Integer priority,
            WorkOrderStatus status);

    // ==========================================================
    // PAGINATION
    // ==========================================================

    Page<WorkOrder> findAll(
            Pageable pageable);

}