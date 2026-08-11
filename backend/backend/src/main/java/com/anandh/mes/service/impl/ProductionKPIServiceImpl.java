package com.anandh.mes.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anandh.mes.dto.ProductionKPIDTO;
import com.anandh.mes.entity.Batch;
import com.anandh.mes.entity.ProductionKPI;
import com.anandh.mes.entity.WorkOrder;
import com.anandh.mes.enums.KpiType;
import com.anandh.mes.exception.ResourceNotFoundException;
import com.anandh.mes.repository.BatchRepository;
import com.anandh.mes.repository.ProductionKPIRepository;
import com.anandh.mes.repository.WorkOrderRepository;
import com.anandh.mes.service.ProductionKPIService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductionKPIServiceImpl
        implements ProductionKPIService {

    private final ProductionKPIRepository kpiRepository;

    private final WorkOrderRepository workOrderRepository;

    private final BatchRepository batchRepository;

    // ==========================================================
    // CREATE KPI
    // ==========================================================

    @Override
    public ProductionKPIDTO createKPI(
            ProductionKPIDTO dto) {

        validateKPIValue(dto.getKpiValue());

        validateCalculationPeriod(
                dto.getCalculationStart(),
                dto.getCalculationEnd());

        WorkOrder workOrder = null;

        if (dto.getWorkOrderId() != null) {

            workOrder =
                    workOrderRepository.findById(
                            dto.getWorkOrderId())
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Work order not found"));
        }

        Batch batch = null;

        if (dto.getBatchId() != null) {

            batch =
                    batchRepository.findById(
                            dto.getBatchId())
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Batch not found"));
        }

        ProductionKPI kpi =
                ProductionKPI.builder()
                        .workOrder(workOrder)
                        .batch(batch)
                        .kpiType(dto.getKpiType())
                        .kpiValue(dto.getKpiValue())
                        .unit(dto.getUnit())
                        .calculationStart(
                                dto.getCalculationStart())
                        .calculationEnd(
                                dto.getCalculationEnd())
                        .remarks(dto.getRemarks())
                        .build();

        ProductionKPI saved =
                kpiRepository.save(kpi);

        return mapToDTO(saved);
    }

    // ==========================================================
    // GET ALL
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<ProductionKPIDTO> getAllKPIs() {

        return kpiRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // GET BY ID
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public ProductionKPIDTO getKPIById(
            Long id) {

        ProductionKPI kpi =
                getEntity(id);

        return mapToDTO(kpi);
    }

    // ==========================================================
    // DELETE
    // ==========================================================

    @Override
    public void deleteKPI(Long id) {

        ProductionKPI kpi =
                getEntity(id);

        kpiRepository.delete(kpi);
    }

    // ==========================================================
    // BY WORK ORDER
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<ProductionKPIDTO>
            getByWorkOrder(Long workOrderId) {

        return kpiRepository
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
    public List<ProductionKPIDTO>
            getByBatch(Long batchId) {

        return kpiRepository
                .findByBatchBatchId(batchId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // BY KPI TYPE
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<ProductionKPIDTO>
            getByKpiType(KpiType kpiType) {

        return kpiRepository
                .findByKpiType(kpiType)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // WORK ORDER + KPI TYPE
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<ProductionKPIDTO>
            getByWorkOrderAndKpiType(
                    Long workOrderId,
                    KpiType kpiType) {

        return kpiRepository
                .findByWorkOrderWorkOrderIdAndKpiType(
                        workOrderId,
                        kpiType)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // BATCH + KPI TYPE
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<ProductionKPIDTO>
            getByBatchAndKpiType(
                    Long batchId,
                    KpiType kpiType) {

        return kpiRepository
                .findByBatchBatchIdAndKpiType(
                        batchId,
                        kpiType)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // CALCULATION PERIOD
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<ProductionKPIDTO>
            getByCalculationStartBetween(
                    LocalDateTime start,
                    LocalDateTime end) {

        validateCalculationPeriod(start, end);

        return kpiRepository
                .findByCalculationStartBetween(
                        start,
                        end)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // WORK ORDER + PERIOD
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<ProductionKPIDTO>
            getByWorkOrderAndCalculationStartBetween(
                    Long workOrderId,
                    LocalDateTime start,
                    LocalDateTime end) {

        validateCalculationPeriod(start, end);

        return kpiRepository
                .findByWorkOrderWorkOrderIdAndCalculationStartBetween(
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
    public Page<ProductionKPIDTO>
            getKPIPage(
                    int page,
                    int size,
                    String sortBy) {

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by(sortBy));

        return kpiRepository
                .findAll(pageable)
                .map(this::mapToDTO);
    }

    // ==========================================================
    // OEE CALCULATION
    // ==========================================================

    public double calculateOEE(
            double availability,
            double performance,
            double quality) {

        validatePercentage(availability);
        validatePercentage(performance);
        validatePercentage(quality);

        return availability
                * performance
                * quality
                / 10000.0;
    }

    // ==========================================================
    // AVAILABILITY
    // ==========================================================

    public double calculateAvailability(
            double runTime,
            double plannedProductionTime) {

        if (plannedProductionTime <= 0) {

            throw new IllegalArgumentException(
                    "Planned production time must be greater than zero");
        }

        double availability =
                (runTime / plannedProductionTime) * 100.0;

        return round(availability);
    }

    // ==========================================================
    // PERFORMANCE
    // ==========================================================

    public double calculatePerformance(
            double idealCycleTime,
            double totalCount,
            double runTime) {

        if (runTime <= 0) {

            throw new IllegalArgumentException(
                    "Run time must be greater than zero");
        }

        double performance =
                (idealCycleTime * totalCount)
                / runTime
                * 100.0;

        return round(performance);
    }

    // ==========================================================
    // QUALITY
    // ==========================================================

    public double calculateQuality(
            double goodCount,
            double totalCount) {

        if (totalCount <= 0) {

            throw new IllegalArgumentException(
                    "Total count must be greater than zero");
        }

        double quality =
                (goodCount / totalCount) * 100.0;

        return round(quality);
    }

    // ==========================================================
    // VALIDATION
    // ==========================================================

    private void validateKPIValue(
            Double value) {

        if (value == null) {

            throw new IllegalArgumentException(
                    "KPI value is required");
        }

        if (value < 0) {

            throw new IllegalArgumentException(
                    "KPI value cannot be negative");
        }
    }

    private void validatePercentage(
            double value) {

        if (value < 0 || value > 100) {

            throw new IllegalArgumentException(
                    "Percentage must be between 0 and 100");
        }
    }

    private void validateCalculationPeriod(
            LocalDateTime start,
            LocalDateTime end) {

        if (start != null &&
                end != null &&
                !end.isAfter(start)) {

            throw new IllegalArgumentException(
                    "Calculation end must be after calculation start");
        }
    }

    private double round(double value) {

        return Math.round(value * 100.0) / 100.0;
    }

    // ==========================================================
    // GET ENTITY
    // ==========================================================

    private ProductionKPI getEntity(Long id) {

        return kpiRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Production KPI not found"));
    }

    // ==========================================================
    // ENTITY → DTO
    // ==========================================================

    private ProductionKPIDTO mapToDTO(
            ProductionKPI kpi) {

        return ProductionKPIDTO.builder()
                .productionKpiId(
                        kpi.getProductionKpiId())
                .workOrderId(
                        kpi.getWorkOrder() != null
                                ? kpi.getWorkOrder()
                                        .getWorkOrderId()
                                : null)
                .batchId(
                        kpi.getBatch() != null
                                ? kpi.getBatch()
                                        .getBatchId()
                                : null)
                .kpiType(
                        kpi.getKpiType())
                .kpiValue(
                        kpi.getKpiValue())
                .unit(
                        kpi.getUnit())
                .calculationStart(
                        kpi.getCalculationStart())
                .calculationEnd(
                        kpi.getCalculationEnd())
                .remarks(
                        kpi.getRemarks())
                .build();
    }

}