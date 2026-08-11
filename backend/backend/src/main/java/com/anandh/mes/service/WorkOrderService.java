package com.anandh.mes.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;

import com.anandh.mes.dto.WorkOrderDTO;
import com.anandh.mes.enums.WorkOrderStatus;

public interface WorkOrderService {

    // ==========================================================
    // CRUD
    // ==========================================================

    WorkOrderDTO createWorkOrder(
            WorkOrderDTO dto);

    List<WorkOrderDTO> getAllWorkOrders();

    WorkOrderDTO getWorkOrderById(
            Long id);

    WorkOrderDTO updateWorkOrder(
            Long id,
            WorkOrderDTO dto);

    void deleteWorkOrder(
            Long id);

    // ==========================================================
    // SEARCH BY STATUS
    // ==========================================================

    List<WorkOrderDTO> getByStatus(
            WorkOrderStatus status);

    // ==========================================================
    // SEARCH BY PRODUCTION SCHEDULE
    // ==========================================================

    List<WorkOrderDTO> getByProductionSchedule(
            Long productionScheduleId);

    // ==========================================================
    // SEARCH BY BATCH
    // ==========================================================

    List<WorkOrderDTO> getByBatch(
            Long batchId);

    // ==========================================================
    // SEARCH BY PRODUCT
    // ==========================================================

    List<WorkOrderDTO> getByProduct(
            String productName);

    // ==========================================================
    // SEARCH BY PRIORITY
    // ==========================================================

    List<WorkOrderDTO> getByPriority(
            Integer priority);

    // ==========================================================
    // SEARCH BY DATE RANGE
    // ==========================================================

    List<WorkOrderDTO> getByPlannedStartBetween(
            LocalDateTime start,
            LocalDateTime end);

    // ==========================================================
    // PRODUCT + STATUS
    // ==========================================================

    List<WorkOrderDTO> getByProductAndStatus(
            String productName,
            WorkOrderStatus status);

    // ==========================================================
    // PRIORITY + STATUS
    // ==========================================================

    List<WorkOrderDTO> getByPriorityAndStatus(
            Integer priority,
            WorkOrderStatus status);

    // ==========================================================
    // PAGINATION
    // ==========================================================

    Page<WorkOrderDTO> getWorkOrderPage(
            int page,
            int size,
            String sortBy);
    
    WorkOrderDTO releaseWorkOrder(Long id);

    WorkOrderDTO startWorkOrder(Long id);

    WorkOrderDTO holdWorkOrder(Long id);

    WorkOrderDTO resumeWorkOrder(Long id);

    WorkOrderDTO completeWorkOrder(Long id);

    WorkOrderDTO cancelWorkOrder(Long id);

    WorkOrderDTO assignBatch(
            Long workOrderId,
            Long batchId);

}