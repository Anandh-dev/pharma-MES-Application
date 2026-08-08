package com.anandh.mes.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anandh.mes.dto.BatchSummaryDTO;
import com.anandh.mes.dto.DashboardSummaryDTO;
import com.anandh.mes.dto.DeviationSummaryDTO;
import com.anandh.mes.dto.QualitySummaryDTO;
import com.anandh.mes.service.DashboardService;
import com.anandh.mes.service.ReportingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {

    private final ReportingService reportingService;

    // ==========================================================
    // DASHBOARD SUMMARY
    // ==========================================================

    @Override
    public DashboardSummaryDTO getDashboardSummary() {

        BatchSummaryDTO production =
                reportingService.getBatchSummary();

        QualitySummaryDTO quality =
                reportingService.getQualitySummary();

        DeviationSummaryDTO deviations =
                reportingService.getDeviationSummary();

        return DashboardSummaryDTO.builder()
                .production(production)
                .quality(quality)
                .deviations(deviations)
                .build();
    }

}