package com.anandh.mes.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anandh.mes.dto.ProductionScheduleDTO;
import com.anandh.mes.entity.ProductionSchedule;
import com.anandh.mes.enums.ProductionScheduleStatus;
import com.anandh.mes.exception.ResourceNotFoundException;
import com.anandh.mes.repository.ProductionScheduleRepository;
import com.anandh.mes.service.ProductionScheduleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductionScheduleServiceImpl
        implements ProductionScheduleService {

    private final ProductionScheduleRepository
            productionScheduleRepository;

    // ==========================================================
    // CREATE
    // ==========================================================

    @Override
    public ProductionScheduleDTO createProductionSchedule(
            ProductionScheduleDTO dto) {

        validateScheduleDates(
                dto.getPlannedStart(),
                dto.getPlannedEnd());

        if (productionScheduleRepository
                .findByScheduleNumber(dto.getScheduleNumber())
                .isPresent()) {

            throw new IllegalArgumentException(
                    "Schedule number already exists");
        }

        ProductionSchedule schedule =
                mapToEntity(dto);

        ProductionSchedule saved =
                productionScheduleRepository.save(schedule);

        return mapToDTO(saved);
    }

    // ==========================================================
    // GET ALL
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<ProductionScheduleDTO>
            getAllProductionSchedules() {

        return productionScheduleRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // GET BY ID
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public ProductionScheduleDTO
            getProductionScheduleById(Long id) {

        ProductionSchedule schedule =
                productionScheduleRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Production schedule not found"));

        return mapToDTO(schedule);
    }

    // ==========================================================
    // UPDATE
    // ==========================================================

    @Override
    public ProductionScheduleDTO updateProductionSchedule(
            Long id,
            ProductionScheduleDTO dto) {

        validateScheduleDates(
                dto.getPlannedStart(),
                dto.getPlannedEnd());

        ProductionSchedule schedule =
                productionScheduleRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Production schedule not found"));

        if (!schedule.getScheduleNumber()
                .equals(dto.getScheduleNumber())) {

            if (productionScheduleRepository
                    .findByScheduleNumber(
                            dto.getScheduleNumber())
                    .isPresent()) {

                throw new IllegalArgumentException(
                        "Schedule number already exists");
            }
        }

        schedule.setScheduleNumber(
                dto.getScheduleNumber());

        schedule.setProductName(
                dto.getProductName());

        schedule.setPlannedQuantity(
                dto.getPlannedQuantity());

        schedule.setUnit(
                dto.getUnit());

        schedule.setPlannedStart(
                dto.getPlannedStart());

        schedule.setPlannedEnd(
                dto.getPlannedEnd());

        schedule.setStatus(
                dto.getStatus());

        schedule.setPriority(
                dto.getPriority());

        schedule.setRemarks(
                dto.getRemarks());

        ProductionSchedule updated =
                productionScheduleRepository.save(schedule);

        return mapToDTO(updated);
    }

    // ==========================================================
    // DELETE
    // ==========================================================

    @Override
    public void deleteProductionSchedule(Long id) {

        ProductionSchedule schedule =
                productionScheduleRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Production schedule not found"));

        productionScheduleRepository.delete(schedule);
    }

    // ==========================================================
    // SEARCH BY STATUS
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<ProductionScheduleDTO> getByStatus(
            ProductionScheduleStatus status) {

        return productionScheduleRepository
                .findByStatus(status)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // SEARCH BY PRODUCT
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<ProductionScheduleDTO> getByProduct(
            String productName) {

        return productionScheduleRepository
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
    public List<ProductionScheduleDTO> getByPriority(
            Integer priority) {

        return productionScheduleRepository
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
    public List<ProductionScheduleDTO>
            getByPlannedStartBetween(
                    LocalDateTime start,
                    LocalDateTime end) {

        if (start.isAfter(end)) {
            throw new IllegalArgumentException(
                    "Start date must be before end date");
        }

        return productionScheduleRepository
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
    public List<ProductionScheduleDTO>
            getByProductAndStatus(
                    String productName,
                    ProductionScheduleStatus status) {

        return productionScheduleRepository
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
    public List<ProductionScheduleDTO>
            getByPriorityAndStatus(
                    Integer priority,
                    ProductionScheduleStatus status) {

        return productionScheduleRepository
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
    public Page<ProductionScheduleDTO>
            getProductionSchedulePage(
                    int page,
                    int size,
                    String sortBy) {

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by(sortBy));

        return productionScheduleRepository
                .findAll(pageable)
                .map(this::mapToDTO);
    }

    // ==========================================================
    // DATE VALIDATION
    // ==========================================================

    private void validateScheduleDates(
            LocalDateTime plannedStart,
            LocalDateTime plannedEnd) {

        if (plannedStart == null ||
                plannedEnd == null) {

            throw new IllegalArgumentException(
                    "Planned start and end are required");
        }

        if (!plannedEnd.isAfter(plannedStart)) {

            throw new IllegalArgumentException(
                    "Planned end must be after planned start");
        }
    }

    // ==========================================================
    // DTO → ENTITY
    // ==========================================================

    private ProductionSchedule mapToEntity(
            ProductionScheduleDTO dto) {

        return ProductionSchedule.builder()
                .scheduleNumber(
                        dto.getScheduleNumber())
                .productName(
                        dto.getProductName())
                .plannedQuantity(
                        dto.getPlannedQuantity())
                .unit(
                        dto.getUnit())
                .plannedStart(
                        dto.getPlannedStart())
                .plannedEnd(
                        dto.getPlannedEnd())
                .status(
                        dto.getStatus())
                .priority(
                        dto.getPriority())
                .remarks(
                        dto.getRemarks())
                .build();
    }

    // ==========================================================
    // ENTITY → DTO
    // ==========================================================

    private ProductionScheduleDTO mapToDTO(
            ProductionSchedule schedule) {

        return ProductionScheduleDTO.builder()
                .productionScheduleId(
                        schedule.getProductionScheduleId())
                .scheduleNumber(
                        schedule.getScheduleNumber())
                .productName(
                        schedule.getProductName())
                .plannedQuantity(
                        schedule.getPlannedQuantity())
                .unit(
                        schedule.getUnit())
                .plannedStart(
                        schedule.getPlannedStart())
                .plannedEnd(
                        schedule.getPlannedEnd())
                .status(
                        schedule.getStatus())
                .priority(
                        schedule.getPriority())
                .remarks(
                        schedule.getRemarks())
                .build();
    }

}