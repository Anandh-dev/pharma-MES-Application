package com.anandh.mes.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;

import com.anandh.mes.dto.WorkOrderAssignmentDTO;
import com.anandh.mes.enums.AssignmentStatus;

public interface WorkOrderAssignmentService {

    // ==========================================================
    // CRUD
    // ==========================================================

    WorkOrderAssignmentDTO createAssignment(
            WorkOrderAssignmentDTO dto);

    List<WorkOrderAssignmentDTO> getAllAssignments();

    WorkOrderAssignmentDTO getAssignmentById(
            Long id);

    WorkOrderAssignmentDTO updateAssignment(
            Long id,
            WorkOrderAssignmentDTO dto);

    void deleteAssignment(
            Long id);

    // ==========================================================
    // SEARCH BY WORK ORDER
    // ==========================================================

    List<WorkOrderAssignmentDTO> getByWorkOrder(
            Long workOrderId);

    // ==========================================================
    // SEARCH BY OPERATOR
    // ==========================================================

    List<WorkOrderAssignmentDTO> getByOperator(
            String operatorName);

    // ==========================================================
    // SEARCH BY WORK CENTER
    // ==========================================================

    List<WorkOrderAssignmentDTO> getByWorkCenter(
            String workCenter);

    // ==========================================================
    // SEARCH BY OPERATION
    // ==========================================================

    List<WorkOrderAssignmentDTO> getByOperation(
            String operationName);

    // ==========================================================
    // SEARCH BY STATUS
    // ==========================================================

    List<WorkOrderAssignmentDTO> getByStatus(
            AssignmentStatus status);

    // ==========================================================
    // OPERATOR + STATUS
    // ==========================================================

    List<WorkOrderAssignmentDTO>
    getByOperatorAndStatus(
            String operatorName,
            AssignmentStatus status);

    // ==========================================================
    // WORK CENTER + STATUS
    // ==========================================================

    List<WorkOrderAssignmentDTO>
    getByWorkCenterAndStatus(
            String workCenter,
            AssignmentStatus status);

    // ==========================================================
    // DATE RANGE
    // ==========================================================

    List<WorkOrderAssignmentDTO>
    getByAssignedAtBetween(
            LocalDateTime start,
            LocalDateTime end);

    // ==========================================================
    // PAGINATION
    // ==========================================================

    Page<WorkOrderAssignmentDTO>
    getAssignmentPage(
            int page,
            int size,
            String sortBy);
    
 // ==========================================================
 // ASSIGNMENT LIFECYCLE
 // ==========================================================

 WorkOrderAssignmentDTO acceptAssignment(
         Long id);

 WorkOrderAssignmentDTO startAssignment(
         Long id);

 WorkOrderAssignmentDTO completeAssignment(
         Long id);

 WorkOrderAssignmentDTO cancelAssignment(
         Long id);

}