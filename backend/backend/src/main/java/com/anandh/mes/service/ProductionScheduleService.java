package com.anandh.mes.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;

import com.anandh.mes.dto.ProductionScheduleDTO;
import com.anandh.mes.enums.ProductionScheduleStatus;

public interface ProductionScheduleService {

    // ==========================================================
    // CRUD
    // ==========================================================

    ProductionScheduleDTO createProductionSchedule(
            ProductionScheduleDTO dto);

    List<ProductionScheduleDTO> getAllProductionSchedules();

    ProductionScheduleDTO getProductionScheduleById(
            Long id);

    ProductionScheduleDTO updateProductionSchedule(
            Long id,
            ProductionScheduleDTO dto);

    void deleteProductionSchedule(
            Long id);

    // ==========================================================
    // SEARCH
    // ==========================================================

    List<ProductionScheduleDTO> getByStatus(
            ProductionScheduleStatus status);

    List<ProductionScheduleDTO> getByProduct(
            String productName);

    List<ProductionScheduleDTO> getByPriority(
            Integer priority);

    List<ProductionScheduleDTO> getByPlannedStartBetween(
            LocalDateTime start,
            LocalDateTime end);

    List<ProductionScheduleDTO> getByProductAndStatus(
            String productName,
            ProductionScheduleStatus status);

    List<ProductionScheduleDTO> getByPriorityAndStatus(
            Integer priority,
            ProductionScheduleStatus status);

    // ==========================================================
    // PAGINATION
    // ==========================================================

    Page<ProductionScheduleDTO> getProductionSchedulePage(
            int page,
            int size,
            String sortBy);

}