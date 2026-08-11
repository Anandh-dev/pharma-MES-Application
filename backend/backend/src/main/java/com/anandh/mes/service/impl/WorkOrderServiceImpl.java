package com.anandh.mes.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anandh.mes.dto.WorkOrderDTO;
import com.anandh.mes.entity.Batch;
import com.anandh.mes.entity.ProductionSchedule;
import com.anandh.mes.entity.WorkOrder;
import com.anandh.mes.enums.WorkOrderStatus;
import com.anandh.mes.exception.ResourceNotFoundException;
import com.anandh.mes.repository.BatchRepository;
import com.anandh.mes.repository.ProductionScheduleRepository;
import com.anandh.mes.repository.WorkOrderRepository;
import com.anandh.mes.service.WorkOrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkOrderServiceImpl implements WorkOrderService {

    private final WorkOrderRepository workOrderRepository;

    private final ProductionScheduleRepository
            productionScheduleRepository;

    private final BatchRepository batchRepository;

    // ==========================================================
    // CREATE
    // ==========================================================

    @Override
    public WorkOrderDTO createWorkOrder(
            WorkOrderDTO dto) {

        validateDates(
                dto.getPlannedStart(),
                dto.getPlannedEnd());

        if (workOrderRepository
                .findByWorkOrderNumber(
                        dto.getWorkOrderNumber())
                .isPresent()) {

            throw new IllegalArgumentException(
                    "Work order number already exists");
        }

        ProductionSchedule productionSchedule =
                productionScheduleRepository
                        .findById(dto.getProductionScheduleId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Production schedule not found"));

        WorkOrder workOrder =
                mapToEntity(dto, productionSchedule);

        if (dto.getBatchId() != null) {

            Batch batch =
                    batchRepository.findById(
                            dto.getBatchId())
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Batch not found"));

            workOrder.setBatch(batch);
        }

        WorkOrder saved =
                workOrderRepository.save(workOrder);

        return mapToDTO(saved);
    }

    // ==========================================================
    // GET ALL
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<WorkOrderDTO> getAllWorkOrders() {

        return workOrderRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // GET BY ID
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public WorkOrderDTO getWorkOrderById(
            Long id) {

        WorkOrder workOrder =
                workOrderRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Work order not found"));

        return mapToDTO(workOrder);
    }

    // ==========================================================
    // UPDATE
    // ==========================================================

    @Override
    public WorkOrderDTO updateWorkOrder(
            Long id,
            WorkOrderDTO dto) {

        validateDates(
                dto.getPlannedStart(),
                dto.getPlannedEnd());

        WorkOrder workOrder =
                workOrderRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Work order not found"));

        if (!workOrder.getWorkOrderNumber()
                .equals(dto.getWorkOrderNumber())) {

            if (workOrderRepository
                    .findByWorkOrderNumber(
                            dto.getWorkOrderNumber())
                    .isPresent()) {

                throw new IllegalArgumentException(
                        "Work order number already exists");
            }
        }

        ProductionSchedule productionSchedule =
                productionScheduleRepository
                        .findById(dto.getProductionScheduleId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Production schedule not found"));

        workOrder.setWorkOrderNumber(
                dto.getWorkOrderNumber());

        workOrder.setProductionSchedule(
                productionSchedule);

        workOrder.setProductName(
                dto.getProductName());

        workOrder.setPlannedQuantity(
                dto.getPlannedQuantity());

        workOrder.setUnit(
                dto.getUnit());

        workOrder.setPriority(
                dto.getPriority());

        workOrder.setPlannedStart(
                dto.getPlannedStart());

        workOrder.setPlannedEnd(
                dto.getPlannedEnd());

        workOrder.setRemarks(
                dto.getRemarks());

        /*
         * Status and actual execution timestamps
         * are controlled by lifecycle methods.
         */

        WorkOrder updated =
                workOrderRepository.save(workOrder);

        return mapToDTO(updated);
    }

    // ==========================================================
    // DELETE
    // ==========================================================

    @Override
    public void deleteWorkOrder(Long id) {

        WorkOrder workOrder =
                workOrderRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Work order not found"));

        if (workOrder.getStatus() ==
                WorkOrderStatus.IN_PROGRESS ||
            workOrder.getStatus() ==
                WorkOrderStatus.COMPLETED) {

            throw new IllegalStateException(
                    "Cannot delete an active or completed work order");
        }

        workOrderRepository.delete(workOrder);
    }

    // ==========================================================
    // SEARCH BY STATUS
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<WorkOrderDTO> getByStatus(
            WorkOrderStatus status) {

        return workOrderRepository
                .findByStatus(status)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // SEARCH BY PRODUCTION SCHEDULE
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<WorkOrderDTO> getByProductionSchedule(
            Long productionScheduleId) {

        return workOrderRepository
                .findByProductionScheduleProductionScheduleId(
                        productionScheduleId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // SEARCH BY BATCH
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<WorkOrderDTO> getByBatch(
            Long batchId) {

        return workOrderRepository
                .findByBatchBatchId(batchId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // SEARCH BY PRODUCT
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<WorkOrderDTO> getByProduct(
            String productName) {

        return workOrderRepository
                .findByProductNameContainingIgnoreCase(
                        productName)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // SEARCH BY PRIORITY
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<WorkOrderDTO> getByPriority(
            Integer priority) {

        return workOrderRepository
                .findByPriority(priority)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // SEARCH BY DATE RANGE
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<WorkOrderDTO>
            getByPlannedStartBetween(
                    LocalDateTime start,
                    LocalDateTime end) {

        if (start.isAfter(end)) {
            throw new IllegalArgumentException(
                    "Start date must be before end date");
        }

        return workOrderRepository
                .findByPlannedStartBetween(start, end)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // PRODUCT + STATUS
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<WorkOrderDTO>
            getByProductAndStatus(
                    String productName,
                    WorkOrderStatus status) {

        return workOrderRepository
                .findByProductNameContainingIgnoreCaseAndStatus(
                        productName,
                        status)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // PRIORITY + STATUS
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<WorkOrderDTO>
            getByPriorityAndStatus(
                    Integer priority,
                    WorkOrderStatus status) {

        return workOrderRepository
                .findByPriorityAndStatus(
                        priority,
                        status)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // PAGINATION
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public Page<WorkOrderDTO> getWorkOrderPage(
            int page,
            int size,
            String sortBy) {

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by(sortBy));

        return workOrderRepository
                .findAll(pageable)
                .map(this::mapToDTO);
    }

    // ==========================================================
    // RELEASE
    // ==========================================================

    public WorkOrderDTO releaseWorkOrder(
            Long id) {

        WorkOrder workOrder =
                getEntity(id);

        if (workOrder.getStatus() !=
                WorkOrderStatus.DRAFT) {

            throw new IllegalStateException(
                    "Only DRAFT work orders can be released");
        }

        workOrder.setStatus(
                WorkOrderStatus.RELEASED);

        return mapToDTO(
                workOrderRepository.save(workOrder));
    }

    // ==========================================================
    // START
    // ==========================================================

    public WorkOrderDTO startWorkOrder(
            Long id) {

        WorkOrder workOrder =
                getEntity(id);

        if (workOrder.getStatus() !=
                WorkOrderStatus.RELEASED) {

            throw new IllegalStateException(
                    "Only RELEASED work orders can be started");
        }

        workOrder.setStatus(
                WorkOrderStatus.IN_PROGRESS);

        workOrder.setActualStart(
                LocalDateTime.now());

        return mapToDTO(
                workOrderRepository.save(workOrder));
    }

    // ==========================================================
    // HOLD
    // ==========================================================

    public WorkOrderDTO holdWorkOrder(
            Long id) {

        WorkOrder workOrder =
                getEntity(id);

        if (workOrder.getStatus() !=
                WorkOrderStatus.IN_PROGRESS) {

            throw new IllegalStateException(
                    "Only IN_PROGRESS work orders can be put on hold");
        }

        workOrder.setStatus(
                WorkOrderStatus.ON_HOLD);

        return mapToDTO(
                workOrderRepository.save(workOrder));
    }

    // ==========================================================
    // RESUME
    // ==========================================================

    public WorkOrderDTO resumeWorkOrder(
            Long id) {

        WorkOrder workOrder =
                getEntity(id);

        if (workOrder.getStatus() !=
                WorkOrderStatus.ON_HOLD) {

            throw new IllegalStateException(
                    "Only ON_HOLD work orders can be resumed");
        }

        workOrder.setStatus(
                WorkOrderStatus.IN_PROGRESS);

        return mapToDTO(
                workOrderRepository.save(workOrder));
    }

    // ==========================================================
    // COMPLETE
    // ==========================================================

    public WorkOrderDTO completeWorkOrder(
            Long id) {

        WorkOrder workOrder =
                getEntity(id);

        if (workOrder.getStatus() !=
                WorkOrderStatus.IN_PROGRESS) {

            throw new IllegalStateException(
                    "Only IN_PROGRESS work orders can be completed");
        }

        workOrder.setStatus(
                WorkOrderStatus.COMPLETED);

        workOrder.setActualEnd(
                LocalDateTime.now());

        return mapToDTO(
                workOrderRepository.save(workOrder));
    }

    // ==========================================================
    // CANCEL
    // ==========================================================

    public WorkOrderDTO cancelWorkOrder(
            Long id) {

        WorkOrder workOrder =
                getEntity(id);

        if (workOrder.getStatus() ==
                WorkOrderStatus.IN_PROGRESS ||
            workOrder.getStatus() ==
                WorkOrderStatus.COMPLETED) {

            throw new IllegalStateException(
                    "Cannot cancel an active or completed work order");
        }

        workOrder.setStatus(
                WorkOrderStatus.CANCELLED);

        return mapToDTO(
                workOrderRepository.save(workOrder));
    }

    // ==========================================================
    // ASSIGN BATCH
    // ==========================================================

    public WorkOrderDTO assignBatch(
            Long workOrderId,
            Long batchId) {

        WorkOrder workOrder =
                getEntity(workOrderId);

        if (workOrder.getStatus() ==
                WorkOrderStatus.COMPLETED ||
            workOrder.getStatus() ==
                WorkOrderStatus.CANCELLED) {

            throw new IllegalStateException(
                    "Cannot assign batch to a completed or cancelled work order");
        }

        Batch batch =
                batchRepository.findById(batchId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Batch not found"));

        workOrder.setBatch(batch);

        return mapToDTO(
                workOrderRepository.save(workOrder));
    }

    // ==========================================================
    // GET ENTITY
    // ==========================================================

    private WorkOrder getEntity(Long id) {

        return workOrderRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Work order not found"));
    }

    // ==========================================================
    // DATE VALIDATION
    // ==========================================================

    private void validateDates(
            LocalDateTime start,
            LocalDateTime end) {

        if (start != null &&
                end != null &&
                !end.isAfter(start)) {

            throw new IllegalArgumentException(
                    "Planned end must be after planned start");
        }
    }

    // ==========================================================
    // DTO → ENTITY
    // ==========================================================

    private WorkOrder mapToEntity(
            WorkOrderDTO dto,
            ProductionSchedule productionSchedule) {

        return WorkOrder.builder()
                .workOrderNumber(
                        dto.getWorkOrderNumber())
                .productionSchedule(
                        productionSchedule)
                .productName(
                        dto.getProductName())
                .plannedQuantity(
                        dto.getPlannedQuantity())
                .unit(
                        dto.getUnit())
                .priority(
                        dto.getPriority())
                .status(
                        dto.getStatus())
                .plannedStart(
                        dto.getPlannedStart())
                .plannedEnd(
                        dto.getPlannedEnd())
                .remarks(
                        dto.getRemarks())
                .build();
    }

    // ==========================================================
    // ENTITY → DTO
    // ==========================================================

    private WorkOrderDTO mapToDTO(
            WorkOrder workOrder) {

        return WorkOrderDTO.builder()
                .workOrderId(
                        workOrder.getWorkOrderId())
                .workOrderNumber(
                        workOrder.getWorkOrderNumber())
                .productionScheduleId(
                        workOrder.getProductionSchedule()
                                .getProductionScheduleId())
                .batchId(
                        workOrder.getBatch() != null
                                ? workOrder.getBatch()
                                        .getBatchId()
                                : null)
                .productName(
                        workOrder.getProductName())
                .plannedQuantity(
                        workOrder.getPlannedQuantity())
                .unit(
                        workOrder.getUnit())
                .priority(
                        workOrder.getPriority())
                .status(
                        workOrder.getStatus())
                .plannedStart(
                        workOrder.getPlannedStart())
                .plannedEnd(
                        workOrder.getPlannedEnd())
                .actualStart(
                        workOrder.getActualStart())
                .actualEnd(
                        workOrder.getActualEnd())
                .remarks(
                        workOrder.getRemarks())
                .build();
    }

}