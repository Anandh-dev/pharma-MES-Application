package com.anandh.mes.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;

import com.anandh.mes.dto.ProductionKPIDTO;
import com.anandh.mes.enums.KpiType;

public interface ProductionKPIService {

    // ==========================================================
    // CRUD
    // ==========================================================

    ProductionKPIDTO createKPI(
            ProductionKPIDTO dto);

    List<ProductionKPIDTO> getAllKPIs();

    ProductionKPIDTO getKPIById(
            Long id);

    void deleteKPI(
            Long id);

    // ==========================================================
    // SEARCH BY WORK ORDER
    // ==========================================================

    List<ProductionKPIDTO> getByWorkOrder(
            Long workOrderId);

    // ==========================================================
    // SEARCH BY BATCH
    // ==========================================================

    List<ProductionKPIDTO> getByBatch(
            Long batchId);

    // ==========================================================
    // SEARCH BY KPI TYPE
    // ==========================================================

    List<ProductionKPIDTO> getByKpiType(
            KpiType kpiType);

    // ==========================================================
    // WORK ORDER + KPI TYPE
    // ==========================================================

    List<ProductionKPIDTO>
    getByWorkOrderAndKpiType(
            Long workOrderId,
            KpiType kpiType);

    // ==========================================================
    // BATCH + KPI TYPE
    // ==========================================================

    List<ProductionKPIDTO>
    getByBatchAndKpiType(
            Long batchId,
            KpiType kpiType);

    // ==========================================================
    // CALCULATION PERIOD
    // ==========================================================

    List<ProductionKPIDTO>
    getByCalculationStartBetween(
            LocalDateTime start,
            LocalDateTime end);

    // ==========================================================
    // WORK ORDER + CALCULATION PERIOD
    // ==========================================================

    List<ProductionKPIDTO>
    getByWorkOrderAndCalculationStartBetween(
            Long workOrderId,
            LocalDateTime start,
            LocalDateTime end);

    // ==========================================================
    // PAGINATION
    // ==========================================================

    Page<ProductionKPIDTO> getKPIPage(
            int page,
            int size,
            String sortBy);
    
    double calculateOEE(
            double availability,
            double performance,
            double quality);

    double calculateAvailability(
            double runTime,
            double plannedProductionTime);

    double calculatePerformance(
            double idealCycleTime,
            double totalCount,
            double runTime);

    double calculateQuality(
            double goodCount,
            double totalCount);
}