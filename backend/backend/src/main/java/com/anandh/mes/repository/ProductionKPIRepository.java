package com.anandh.mes.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.anandh.mes.entity.ProductionKPI;
import com.anandh.mes.enums.KpiType;

public interface ProductionKPIRepository
        extends JpaRepository<ProductionKPI, Long> {

    // ==========================================================
    // SEARCH BY WORK ORDER
    // ==========================================================

    List<ProductionKPI>
    findByWorkOrderWorkOrderId(Long workOrderId);

    // ==========================================================
    // SEARCH BY BATCH
    // ==========================================================

    List<ProductionKPI>
    findByBatchBatchId(Long batchId);

    // ==========================================================
    // SEARCH BY KPI TYPE
    // ==========================================================

    List<ProductionKPI>
    findByKpiType(KpiType kpiType);

    // ==========================================================
    // WORK ORDER + KPI TYPE
    // ==========================================================

    List<ProductionKPI>
    findByWorkOrderWorkOrderIdAndKpiType(
            Long workOrderId,
            KpiType kpiType);

    // ==========================================================
    // BATCH + KPI TYPE
    // ==========================================================

    List<ProductionKPI>
    findByBatchBatchIdAndKpiType(
            Long batchId,
            KpiType kpiType);

    // ==========================================================
    // CALCULATION PERIOD
    // ==========================================================

    List<ProductionKPI>
    findByCalculationStartBetween(
            LocalDateTime start,
            LocalDateTime end);

    // ==========================================================
    // WORK ORDER + CALCULATION PERIOD
    // ==========================================================

    List<ProductionKPI>
    findByWorkOrderWorkOrderIdAndCalculationStartBetween(
            Long workOrderId,
            LocalDateTime start,
            LocalDateTime end);

    // ==========================================================
    // PAGINATION
    // ==========================================================

    Page<ProductionKPI> findAll(
            Pageable pageable);
}