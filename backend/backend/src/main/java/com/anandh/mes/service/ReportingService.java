package com.anandh.mes.service;

import java.util.List;

import com.anandh.mes.dto.BatchSummaryDTO;
import com.anandh.mes.dto.DeviationSummaryDTO;
import com.anandh.mes.dto.MaterialConsumptionSummaryDTO;
import com.anandh.mes.dto.QualitySummaryDTO;

public interface ReportingService {

    // ==========================================================
    // BATCH REPORTING
    // ==========================================================

    BatchSummaryDTO getBatchSummary();

    // ==========================================================
    // QUALITY REPORTING
    // ==========================================================

    QualitySummaryDTO getQualitySummary();

    // ==========================================================
    // DEVIATION REPORTING
    // ==========================================================

    DeviationSummaryDTO getDeviationSummary();

    // ==========================================================
    // MATERIAL CONSUMPTION REPORTING
    // ==========================================================

    List<MaterialConsumptionSummaryDTO>
    getMaterialConsumptionSummary();

}