package com.anandh.mes.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.anandh.mes.entity.WorkOrderAssignment;
import com.anandh.mes.enums.AssignmentStatus;

public interface WorkOrderAssignmentRepository
        extends JpaRepository<WorkOrderAssignment, Long> {

    // ==========================================================
    // SEARCH BY WORK ORDER
    // ==========================================================

    List<WorkOrderAssignment>
    findByWorkOrderWorkOrderId(Long workOrderId);

    // ==========================================================
    // SEARCH BY OPERATOR
    // ==========================================================

    List<WorkOrderAssignment>
    findByOperatorNameContainingIgnoreCase(String operatorName);

    // ==========================================================
    // SEARCH BY WORK CENTER
    // ==========================================================

    List<WorkOrderAssignment>
    findByWorkCenterContainingIgnoreCase(String workCenter);

    // ==========================================================
    // SEARCH BY OPERATION
    // ==========================================================

    List<WorkOrderAssignment>
    findByOperationNameContainingIgnoreCase(String operationName);

    // ==========================================================
    // SEARCH BY STATUS
    // ==========================================================

    List<WorkOrderAssignment>
    findByStatus(AssignmentStatus status);

    // ==========================================================
    // OPERATOR + STATUS
    // ==========================================================

    List<WorkOrderAssignment>
    findByOperatorNameContainingIgnoreCaseAndStatus(
            String operatorName,
            AssignmentStatus status);

    // ==========================================================
    // WORK CENTER + STATUS
    // ==========================================================

    List<WorkOrderAssignment>
    findByWorkCenterContainingIgnoreCaseAndStatus(
            String workCenter,
            AssignmentStatus status);

    // ==========================================================
    // ASSIGNED DATE RANGE
    // ==========================================================

    List<WorkOrderAssignment>
    findByAssignedAtBetween(
            LocalDateTime start,
            LocalDateTime end);

    // ==========================================================
    // PAGINATION
    // ==========================================================

    Page<WorkOrderAssignment> findAll(
            Pageable pageable);

}