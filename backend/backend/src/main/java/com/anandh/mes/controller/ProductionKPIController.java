package com.anandh.mes.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.anandh.mes.dto.ProductionKPIDTO;
import com.anandh.mes.enums.KpiType;
import com.anandh.mes.service.ProductionKPIService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/production-kpis")
@RequiredArgsConstructor
public class ProductionKPIController {

    private final ProductionKPIService kpiService;

    // ==========================================================
    // CREATE
    // ==========================================================

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductionKPIDTO createKPI(
            @Valid @RequestBody ProductionKPIDTO dto) {

        return kpiService.createKPI(dto);
    }

    // ==========================================================
    // GET ALL
    // ==========================================================

    @GetMapping
    public List<ProductionKPIDTO> getAllKPIs() {

        return kpiService.getAllKPIs();
    }

    // ==========================================================
    // GET BY ID
    // ==========================================================

    @GetMapping("/{id}")
    public ProductionKPIDTO getKPIById(
            @PathVariable Long id) {

        return kpiService.getKPIById(id);
    }

    // ==========================================================
    // DELETE
    // ==========================================================

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteKPI(
            @PathVariable Long id) {

        kpiService.deleteKPI(id);
    }

    // ==========================================================
    // BY WORK ORDER
    // ==========================================================

    @GetMapping("/work-order/{workOrderId}")
    public List<ProductionKPIDTO> getByWorkOrder(
            @PathVariable Long workOrderId) {

        return kpiService.getByWorkOrder(workOrderId);
    }

    // ==========================================================
    // BY BATCH
    // ==========================================================

    @GetMapping("/batch/{batchId}")
    public List<ProductionKPIDTO> getByBatch(
            @PathVariable Long batchId) {

        return kpiService.getByBatch(batchId);
    }

    // ==========================================================
    // BY KPI TYPE
    // ==========================================================

    @GetMapping("/type/{kpiType}")
    public List<ProductionKPIDTO> getByKpiType(
            @PathVariable KpiType kpiType) {

        return kpiService.getByKpiType(kpiType);
    }

    // ==========================================================
    // WORK ORDER + KPI TYPE
    // ==========================================================

    @GetMapping(
            "/work-order/{workOrderId}/type/{kpiType}")
    public List<ProductionKPIDTO>
            getByWorkOrderAndKpiType(

            @PathVariable Long workOrderId,

            @PathVariable KpiType kpiType) {

        return kpiService
                .getByWorkOrderAndKpiType(
                        workOrderId,
                        kpiType);
    }

    // ==========================================================
    // BATCH + KPI TYPE
    // ==========================================================

    @GetMapping(
            "/batch/{batchId}/type/{kpiType}")
    public List<ProductionKPIDTO>
            getByBatchAndKpiType(

            @PathVariable Long batchId,

            @PathVariable KpiType kpiType) {

        return kpiService
                .getByBatchAndKpiType(
                        batchId,
                        kpiType);
    }

    // ==========================================================
    // DATE RANGE
    // ==========================================================

    @GetMapping("/date-range")
    public List<ProductionKPIDTO>
            getByCalculationStartBetween(

            @RequestParam LocalDateTime start,

            @RequestParam LocalDateTime end) {

        return kpiService
                .getByCalculationStartBetween(
                        start,
                        end);
    }

    // ==========================================================
    // WORK ORDER + DATE RANGE
    // ==========================================================

    @GetMapping(
            "/work-order/{workOrderId}/date-range")
    public List<ProductionKPIDTO>
            getByWorkOrderAndCalculationStartBetween(

            @PathVariable Long workOrderId,

            @RequestParam LocalDateTime start,

            @RequestParam LocalDateTime end) {

        return kpiService
                .getByWorkOrderAndCalculationStartBetween(
                        workOrderId,
                        start,
                        end);
    }

    // ==========================================================
    // PAGINATION
    // ==========================================================

    @GetMapping("/page")
    public Page<ProductionKPIDTO> getKPIPage(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(
                    defaultValue = "productionKpiId")
            String sortBy) {

        return kpiService.getKPIPage(
                page,
                size,
                sortBy);
    }

    // ==========================================================
    // CALCULATE AVAILABILITY
    // ==========================================================

    @GetMapping("/calculate/availability")
    public double calculateAvailability(

            @RequestParam double runTime,

            @RequestParam double plannedProductionTime) {

        return kpiService.calculateAvailability(
                runTime,
                plannedProductionTime);
    }

    // ==========================================================
    // CALCULATE PERFORMANCE
    // ==========================================================

    @GetMapping("/calculate/performance")
    public double calculatePerformance(

            @RequestParam double idealCycleTime,

            @RequestParam double totalCount,

            @RequestParam double runTime) {

        return kpiService.calculatePerformance(
                idealCycleTime,
                totalCount,
                runTime);
    }

    // ==========================================================
    // CALCULATE QUALITY
    // ==========================================================

    @GetMapping("/calculate/quality")
    public double calculateQuality(

            @RequestParam double goodCount,

            @RequestParam double totalCount) {

        return kpiService.calculateQuality(
                goodCount,
                totalCount);
    }

    // ==========================================================
    // CALCULATE OEE
    // ==========================================================

    @GetMapping("/calculate/oee")
    public double calculateOEE(

            @RequestParam double availability,

            @RequestParam double performance,

            @RequestParam double quality) {

        return kpiService.calculateOEE(
                availability,
                performance,
                quality);
    }

}