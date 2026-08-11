package com.anandh.mes.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anandh.mes.dto.ProductionExecutionEventDTO;
import com.anandh.mes.entity.Batch;
import com.anandh.mes.entity.ProductionExecutionEvent;
import com.anandh.mes.entity.WorkOrder;
import com.anandh.mes.entity.WorkOrderAssignment;
import com.anandh.mes.enums.ProductionEventType;
import com.anandh.mes.enums.WorkOrderStatus;
import com.anandh.mes.exception.ResourceNotFoundException;
import com.anandh.mes.repository.BatchRepository;
import com.anandh.mes.repository.ProductionExecutionEventRepository;
import com.anandh.mes.repository.WorkOrderAssignmentRepository;
import com.anandh.mes.repository.WorkOrderRepository;
import com.anandh.mes.service.ProductionExecutionEventService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductionExecutionEventServiceImpl
        implements ProductionExecutionEventService {

    private final ProductionExecutionEventRepository eventRepository;

    private final WorkOrderRepository workOrderRepository;

    private final BatchRepository batchRepository;

    private final WorkOrderAssignmentRepository assignmentRepository;

    // ==========================================================
    // CREATE EVENT
    // ==========================================================

    @Override
    public ProductionExecutionEventDTO createEvent(
            ProductionExecutionEventDTO dto) {

        WorkOrder workOrder =
                workOrderRepository.findById(
                        dto.getWorkOrderId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Work order not found"));

        validateEvent(
                workOrder.getStatus(),
                dto.getEventType());

        Batch batch = null;

        if (dto.getBatchId() != null) {

            batch = batchRepository.findById(
                    dto.getBatchId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Batch not found"));
        }

        WorkOrderAssignment assignment = null;

        if (dto.getAssignmentId() != null) {

            assignment = assignmentRepository.findById(
                    dto.getAssignmentId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Work order assignment not found"));

            if (!assignment.getWorkOrder()
                    .getWorkOrderId()
                    .equals(workOrder.getWorkOrderId())) {

                throw new IllegalArgumentException(
                        "Assignment does not belong to the work order");
            }
        }

        ProductionExecutionEvent event =
                ProductionExecutionEvent.builder()
                        .workOrder(workOrder)
                        .batch(batch)
                        .assignment(assignment)
                        .eventType(dto.getEventType())
                        .operatorName(dto.getOperatorName())
                        .eventTime(dto.getEventTime())
                        .remarks(dto.getRemarks())
                        .build();

        ProductionExecutionEvent saved =
                eventRepository.save(event);

        return mapToDTO(saved);
    }

    // ==========================================================
    // RECORD EVENT
    // ==========================================================

    @Override
    public ProductionExecutionEventDTO recordEvent(
            Long workOrderId,
            ProductionEventType eventType,
            String operatorName,
            Long batchId,
            Long assignmentId,
            String remarks) {

        WorkOrder workOrder =
                workOrderRepository.findById(workOrderId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Work order not found"));

        validateEvent(
                workOrder.getStatus(),
                eventType);

        ProductionExecutionEvent event =
                ProductionExecutionEvent.builder()
                        .workOrder(workOrder)
                        .eventType(eventType)
                        .operatorName(operatorName)
                        .remarks(remarks)
                        .build();

        if (batchId != null) {

            Batch batch =
                    batchRepository.findById(batchId)
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Batch not found"));

            event.setBatch(batch);
        }

        if (assignmentId != null) {

            WorkOrderAssignment assignment =
                    assignmentRepository.findById(
                            assignmentId)
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Work order assignment not found"));

            if (!assignment.getWorkOrder()
                    .getWorkOrderId()
                    .equals(workOrderId)) {

                throw new IllegalArgumentException(
                        "Assignment does not belong to the work order");
            }

            event.setAssignment(assignment);
        }

        ProductionExecutionEvent saved =
                eventRepository.save(event);

        return mapToDTO(saved);
    }

    // ==========================================================
    // GET ALL
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<ProductionExecutionEventDTO> getAllEvents() {

        return eventRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // GET BY ID
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public ProductionExecutionEventDTO getEventById(
            Long id) {

        ProductionExecutionEvent event =
                getEntity(id);

        return mapToDTO(event);
    }

    // ==========================================================
    // DELETE
    // ==========================================================

    @Override
    public void deleteEvent(Long id) {

        ProductionExecutionEvent event =
                getEntity(id);

        eventRepository.delete(event);
    }

    // ==========================================================
    // BY WORK ORDER
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<ProductionExecutionEventDTO>
            getByWorkOrder(Long workOrderId) {

        return eventRepository
                .findByWorkOrderWorkOrderId(workOrderId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // BY BATCH
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<ProductionExecutionEventDTO>
            getByBatch(Long batchId) {

        return eventRepository
                .findByBatchBatchId(batchId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // BY ASSIGNMENT
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<ProductionExecutionEventDTO>
            getByAssignment(Long assignmentId) {

        return eventRepository
                .findByAssignmentAssignmentId(
                        assignmentId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // BY EVENT TYPE
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<ProductionExecutionEventDTO>
            getByEventType(
                    ProductionEventType eventType) {

        return eventRepository
                .findByEventType(eventType)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // BY OPERATOR
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<ProductionExecutionEventDTO>
            getByOperator(String operatorName) {

        return eventRepository
                .findByOperatorNameContainingIgnoreCase(
                        operatorName)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // BY DATE RANGE
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<ProductionExecutionEventDTO>
            getByEventTimeBetween(
                    LocalDateTime start,
                    LocalDateTime end) {

        if (start.isAfter(end)) {
            throw new IllegalArgumentException(
                    "Start date must be before end date");
        }

        return eventRepository
                .findByEventTimeBetween(start, end)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // WORK ORDER + EVENT TYPE
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<ProductionExecutionEventDTO>
            getByWorkOrderAndEventType(
                    Long workOrderId,
                    ProductionEventType eventType) {

        return eventRepository
                .findByWorkOrderWorkOrderIdAndEventType(
                        workOrderId,
                        eventType)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // WORK ORDER + DATE RANGE
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<ProductionExecutionEventDTO>
            getByWorkOrderAndEventTimeBetween(
                    Long workOrderId,
                    LocalDateTime start,
                    LocalDateTime end) {

        if (start.isAfter(end)) {
            throw new IllegalArgumentException(
                    "Start date must be before end date");
        }

        return eventRepository
                .findByWorkOrderWorkOrderIdAndEventTimeBetween(
                        workOrderId,
                        start,
                        end)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // PAGINATION
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public Page<ProductionExecutionEventDTO>
            getEventPage(
                    int page,
                    int size,
                    String sortBy) {

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by(sortBy));

        return eventRepository
                .findAll(pageable)
                .map(this::mapToDTO);
    }

    // ==========================================================
    // EVENT VALIDATION
    // ==========================================================

    private void validateEvent(
            WorkOrderStatus status,
            ProductionEventType eventType) {

        boolean valid = false;

        switch (eventType) {

            case CREATED:
                valid = status == WorkOrderStatus.DRAFT;
                break;

            case RELEASED:
                valid = status == WorkOrderStatus.RELEASED;
                break;

            case STARTED:
                valid = status == WorkOrderStatus.IN_PROGRESS;
                break;

            case PAUSED:
                valid = status == WorkOrderStatus.IN_PROGRESS;
                break;

            case RESUMED:
                valid = status == WorkOrderStatus.IN_PROGRESS;
                break;

            case COMPLETED:
                valid = status == WorkOrderStatus.COMPLETED;
                break;

            case HELD:
                valid = status == WorkOrderStatus.ON_HOLD;
                break;

            case ABORTED:
                valid = status == WorkOrderStatus.IN_PROGRESS
                        || status == WorkOrderStatus.ON_HOLD;
                break;

            case CANCELLED:
                valid = status == WorkOrderStatus.CANCELLED;
                break;
        }

        if (!valid) {
            throw new IllegalStateException(
                    "Event " + eventType +
                    " is not valid for work order status " +
                    status);
        }
    }

    // ==========================================================
    // GET ENTITY
    // ==========================================================

    private ProductionExecutionEvent getEntity(
            Long id) {

        return eventRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Production execution event not found"));
    }

    // ==========================================================
    // ENTITY → DTO
    // ==========================================================

    private ProductionExecutionEventDTO mapToDTO(
            ProductionExecutionEvent event) {

        return ProductionExecutionEventDTO.builder()
                .productionExecutionEventId(
                        event.getProductionExecutionEventId())
                .workOrderId(
                        event.getWorkOrder()
                                .getWorkOrderId())
                .batchId(
                        event.getBatch() != null
                                ? event.getBatch()
                                        .getBatchId()
                                : null)
                .assignmentId(
                        event.getAssignment() != null
                                ? event.getAssignment()
                                        .getAssignmentId()
                                : null)
                .eventType(
                        event.getEventType())
                .operatorName(
                        event.getOperatorName())
                .eventTime(
                        event.getEventTime())
                .remarks(
                        event.getRemarks())
                .build();
    }

}