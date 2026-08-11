package com.anandh.mes.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anandh.mes.dto.WorkOrderAssignmentDTO;
import com.anandh.mes.entity.WorkOrder;
import com.anandh.mes.entity.WorkOrderAssignment;
import com.anandh.mes.enums.AssignmentStatus;
import com.anandh.mes.enums.WorkOrderStatus;
import com.anandh.mes.exception.ResourceNotFoundException;
import com.anandh.mes.repository.WorkOrderAssignmentRepository;
import com.anandh.mes.repository.WorkOrderRepository;
import com.anandh.mes.service.WorkOrderAssignmentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkOrderAssignmentServiceImpl
        implements WorkOrderAssignmentService {

    private final WorkOrderAssignmentRepository
            assignmentRepository;

    private final WorkOrderRepository workOrderRepository;

    // ==========================================================
    // CREATE
    // ==========================================================

    @Override
    public WorkOrderAssignmentDTO createAssignment(
            WorkOrderAssignmentDTO dto) {

        WorkOrder workOrder =
                workOrderRepository.findById(
                        dto.getWorkOrderId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Work order not found"));

        if (workOrder.getStatus() ==
                WorkOrderStatus.CANCELLED ||
            workOrder.getStatus() ==
                WorkOrderStatus.COMPLETED) {

            throw new IllegalStateException(
                    "Cannot assign work to a completed or cancelled work order");
        }

        WorkOrderAssignment assignment =
                WorkOrderAssignment.builder()
                        .workOrder(workOrder)
                        .operatorName(
                                dto.getOperatorName())
                        .workCenter(
                                dto.getWorkCenter())
                        .operationName(
                                dto.getOperationName())
                        .assignedAt(
                                dto.getAssignedAt())
                        .startedAt(
                                dto.getStartedAt())
                        .completedAt(
                                dto.getCompletedAt())
                        .status(
                                dto.getStatus())
                        .remarks(
                                dto.getRemarks())
                        .build();

        WorkOrderAssignment saved =
                assignmentRepository.save(assignment);

        return mapToDTO(saved);
    }

    // ==========================================================
    // GET ALL
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<WorkOrderAssignmentDTO>
            getAllAssignments() {

        return assignmentRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // GET BY ID
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public WorkOrderAssignmentDTO
            getAssignmentById(Long id) {

        WorkOrderAssignment assignment =
                getEntity(id);

        return mapToDTO(assignment);
    }

    // ==========================================================
    // UPDATE
    // ==========================================================

    @Override
    public WorkOrderAssignmentDTO updateAssignment(
            Long id,
            WorkOrderAssignmentDTO dto) {

        WorkOrderAssignment assignment =
                getEntity(id);

        if (assignment.getStatus() ==
                AssignmentStatus.COMPLETED ||
            assignment.getStatus() ==
                AssignmentStatus.CANCELLED) {

            throw new IllegalStateException(
                    "Cannot update a completed or cancelled assignment");
        }

        WorkOrder workOrder =
                workOrderRepository.findById(
                        dto.getWorkOrderId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Work order not found"));

        if (workOrder.getStatus() ==
                WorkOrderStatus.COMPLETED ||
            workOrder.getStatus() ==
                WorkOrderStatus.CANCELLED) {

            throw new IllegalStateException(
                    "Cannot assign work to a completed or cancelled work order");
        }

        assignment.setWorkOrder(workOrder);

        assignment.setOperatorName(
                dto.getOperatorName());

        assignment.setWorkCenter(
                dto.getWorkCenter());

        assignment.setOperationName(
                dto.getOperationName());

        assignment.setRemarks(
                dto.getRemarks());

        WorkOrderAssignment updated =
                assignmentRepository.save(assignment);

        return mapToDTO(updated);
    }

    // ==========================================================
    // DELETE
    // ==========================================================

    @Override
    public void deleteAssignment(Long id) {

        WorkOrderAssignment assignment =
                getEntity(id);

        if (assignment.getStatus() ==
                AssignmentStatus.IN_PROGRESS ||
            assignment.getStatus() ==
                AssignmentStatus.COMPLETED) {

            throw new IllegalStateException(
                    "Cannot delete an active or completed assignment");
        }

        assignmentRepository.delete(assignment);
    }

    // ==========================================================
    // SEARCH BY WORK ORDER
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<WorkOrderAssignmentDTO>
            getByWorkOrder(Long workOrderId) {

        return assignmentRepository
                .findByWorkOrderWorkOrderId(workOrderId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // SEARCH BY OPERATOR
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<WorkOrderAssignmentDTO>
            getByOperator(String operatorName) {

        return assignmentRepository
                .findByOperatorNameContainingIgnoreCase(
                        operatorName)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // SEARCH BY WORK CENTER
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<WorkOrderAssignmentDTO>
            getByWorkCenter(String workCenter) {

        return assignmentRepository
                .findByWorkCenterContainingIgnoreCase(
                        workCenter)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // SEARCH BY OPERATION
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<WorkOrderAssignmentDTO>
            getByOperation(String operationName) {

        return assignmentRepository
                .findByOperationNameContainingIgnoreCase(
                        operationName)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // SEARCH BY STATUS
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<WorkOrderAssignmentDTO>
            getByStatus(AssignmentStatus status) {

        return assignmentRepository
                .findByStatus(status)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // OPERATOR + STATUS
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<WorkOrderAssignmentDTO>
            getByOperatorAndStatus(
                    String operatorName,
                    AssignmentStatus status) {

        return assignmentRepository
                .findByOperatorNameContainingIgnoreCaseAndStatus(
                        operatorName,
                        status)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // WORK CENTER + STATUS
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<WorkOrderAssignmentDTO>
            getByWorkCenterAndStatus(
                    String workCenter,
                    AssignmentStatus status) {

        return assignmentRepository
                .findByWorkCenterContainingIgnoreCaseAndStatus(
                        workCenter,
                        status)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // DATE RANGE
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<WorkOrderAssignmentDTO>
            getByAssignedAtBetween(
                    LocalDateTime start,
                    LocalDateTime end) {

        if (start.isAfter(end)) {
            throw new IllegalArgumentException(
                    "Start date must be before end date");
        }

        return assignmentRepository
                .findByAssignedAtBetween(start, end)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // PAGINATION
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public Page<WorkOrderAssignmentDTO>
            getAssignmentPage(
                    int page,
                    int size,
                    String sortBy) {

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by(sortBy));

        return assignmentRepository
                .findAll(pageable)
                .map(this::mapToDTO);
    }

    // ==========================================================
    // ACCEPT
    // ==========================================================

    public WorkOrderAssignmentDTO acceptAssignment(
            Long id) {

        WorkOrderAssignment assignment =
                getEntity(id);

        if (assignment.getStatus() !=
                AssignmentStatus.ASSIGNED) {

            throw new IllegalStateException(
                    "Only ASSIGNED work can be accepted");
        }

        assignment.setStatus(
                AssignmentStatus.ACCEPTED);

        return mapToDTO(
                assignmentRepository.save(assignment));
    }

    // ==========================================================
    // START
    // ==========================================================

    public WorkOrderAssignmentDTO startAssignment(
            Long id) {

        WorkOrderAssignment assignment =
                getEntity(id);

        if (assignment.getStatus() !=
                AssignmentStatus.ACCEPTED) {

            throw new IllegalStateException(
                    "Only ACCEPTED assignments can be started");
        }

        assignment.setStatus(
                AssignmentStatus.IN_PROGRESS);

        assignment.setStartedAt(
                LocalDateTime.now());

        return mapToDTO(
                assignmentRepository.save(assignment));
    }

    // ==========================================================
    // COMPLETE
    // ==========================================================

    public WorkOrderAssignmentDTO completeAssignment(
            Long id) {

        WorkOrderAssignment assignment =
                getEntity(id);

        if (assignment.getStatus() !=
                AssignmentStatus.IN_PROGRESS) {

            throw new IllegalStateException(
                    "Only IN_PROGRESS assignments can be completed");
        }

        assignment.setStatus(
                AssignmentStatus.COMPLETED);

        assignment.setCompletedAt(
                LocalDateTime.now());

        return mapToDTO(
                assignmentRepository.save(assignment));
    }

    // ==========================================================
    // CANCEL
    // ==========================================================

    public WorkOrderAssignmentDTO cancelAssignment(
            Long id) {

        WorkOrderAssignment assignment =
                getEntity(id);

        if (assignment.getStatus() ==
                AssignmentStatus.IN_PROGRESS ||
            assignment.getStatus() ==
                AssignmentStatus.COMPLETED) {

            throw new IllegalStateException(
                    "Cannot cancel an active or completed assignment");
        }

        assignment.setStatus(
                AssignmentStatus.CANCELLED);

        return mapToDTO(
                assignmentRepository.save(assignment));
    }

    // ==========================================================
    // GET ENTITY
    // ==========================================================

    private WorkOrderAssignment getEntity(Long id) {

        return assignmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Work order assignment not found"));
    }

    // ==========================================================
    // ENTITY → DTO
    // ==========================================================

    private WorkOrderAssignmentDTO mapToDTO(
            WorkOrderAssignment assignment) {

        return WorkOrderAssignmentDTO.builder()
                .assignmentId(
                        assignment.getAssignmentId())
                .workOrderId(
                        assignment.getWorkOrder()
                                .getWorkOrderId())
                .operatorName(
                        assignment.getOperatorName())
                .workCenter(
                        assignment.getWorkCenter())
                .operationName(
                        assignment.getOperationName())
                .assignedAt(
                        assignment.getAssignedAt())
                .startedAt(
                        assignment.getStartedAt())
                .completedAt(
                        assignment.getCompletedAt())
                .status(
                        assignment.getStatus())
                .remarks(
                        assignment.getRemarks())
                .build();
    }

}