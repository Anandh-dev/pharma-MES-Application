package com.anandh.mes.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anandh.mes.dto.BatchSummaryDTO;
import com.anandh.mes.dto.DeviationSummaryDTO;
import com.anandh.mes.dto.MaterialConsumptionSummaryDTO;
import com.anandh.mes.dto.QualitySummaryDTO;
import com.anandh.mes.service.ReportingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportingController {

    private final ReportingService reportingService;

    // ==========================================================
    // BATCH REPORT
    // ==========================================================

    @GetMapping("/batches/summary")
    public BatchSummaryDTO getBatchSummary() {

        return reportingService.getBatchSummary();
    }

    // ==========================================================
    // QUALITY REPORT
    // ==========================================================

    @GetMapping("/quality/summary")
    public QualitySummaryDTO getQualitySummary() {

        return reportingService.getQualitySummary();
    }

    // ==========================================================
    // DEVIATION REPORT
    // ==========================================================

    @GetMapping("/deviations/summary")
    public DeviationSummaryDTO getDeviationSummary() {

        return reportingService.getDeviationSummary();
    }

    // ==========================================================
    // MATERIAL CONSUMPTION REPORT
    // ==========================================================

    @GetMapping("/material-consumption")
    public List<MaterialConsumptionSummaryDTO>
            getMaterialConsumptionSummary() {

        return reportingService
                .getMaterialConsumptionSummary();
    }

}