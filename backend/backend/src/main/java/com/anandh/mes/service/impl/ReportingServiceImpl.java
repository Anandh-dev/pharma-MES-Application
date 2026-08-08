package com.anandh.mes.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anandh.mes.dto.BatchSummaryDTO;
import com.anandh.mes.dto.DeviationSummaryDTO;
import com.anandh.mes.dto.MaterialConsumptionSummaryDTO;
import com.anandh.mes.dto.QualitySummaryDTO;
import com.anandh.mes.enums.BatchStatus;
import com.anandh.mes.enums.DeviationSeverity;
import com.anandh.mes.enums.DeviationStatus;
import com.anandh.mes.enums.InspectionStatus;
import com.anandh.mes.repository.BatchRepository;
import com.anandh.mes.repository.DeviationRepository;
import com.anandh.mes.repository.MaterialConsumptionRepository;
import com.anandh.mes.repository.QualityInspectionRepository;
import com.anandh.mes.service.ReportingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportingServiceImpl implements ReportingService {

    private final BatchRepository batchRepository;

    private final QualityInspectionRepository qualityInspectionRepository;

    private final DeviationRepository deviationRepository;

    private final MaterialConsumptionRepository materialConsumptionRepository;

    // ==========================================================
    // BATCH SUMMARY
    // ==========================================================

    @Override
    public BatchSummaryDTO getBatchSummary() {

        Long totalBatches =
                batchRepository.countTotalBatches();

        Long completedBatches =
                batchRepository.countBatchesByStatus(
                        BatchStatus.COMPLETED);

        Long inProgressBatches =
                batchRepository.countBatchesByStatus(
                        BatchStatus.IN_PROGRESS);

        Long onHoldBatches =
                batchRepository.countBatchesByStatus(
                        BatchStatus.ON_HOLD);

        Long failedBatches =
                batchRepository.countBatchesByStatus(
                        BatchStatus.FAILED);

        return BatchSummaryDTO.builder()
                .totalBatches(totalBatches)
                .completedBatches(completedBatches)
                .inProgressBatches(inProgressBatches)
                .onHoldBatches(onHoldBatches)
                .failedBatches(failedBatches)
                .build();
    }

    // ==========================================================
    // QUALITY SUMMARY
    // ==========================================================

    @Override
    public QualitySummaryDTO getQualitySummary() {

        Long totalInspections =
                qualityInspectionRepository
                        .countTotalInspections();

        Long passedInspections =
                qualityInspectionRepository
                        .countInspectionsByStatus(
                                InspectionStatus.PASSED);

        Long failedInspections =
                qualityInspectionRepository
                        .countInspectionsByStatus(
                                InspectionStatus.FAILED);

        Long pendingInspections =
                qualityInspectionRepository
                        .countInspectionsByStatus(
                                InspectionStatus.PENDING);

        return QualitySummaryDTO.builder()
                .totalInspections(totalInspections)
                .passedInspections(passedInspections)
                .failedInspections(failedInspections)
                .pendingInspections(pendingInspections)
                .build();
    }

    // ==========================================================
    // DEVIATION SUMMARY
    // ==========================================================

    @Override
    public DeviationSummaryDTO getDeviationSummary() {

        Long totalDeviations =
                deviationRepository.countTotalDeviations();

        Long openDeviations =
                deviationRepository.countDeviationsByStatus(
                        DeviationStatus.OPEN);

        Long inProgressDeviations =
                deviationRepository.countDeviationsByStatus(
                        DeviationStatus.IN_PROGRESS);

        Long closedDeviations =
                deviationRepository.countDeviationsByStatus(
                        DeviationStatus.CLOSED);

        Long criticalDeviations =
                deviationRepository.countDeviationsBySeverity(
                        DeviationSeverity.CRITICAL);

        Long highDeviations =
                deviationRepository.countDeviationsBySeverity(
                        DeviationSeverity.HIGH);

        return DeviationSummaryDTO.builder()
                .totalDeviations(totalDeviations)
                .openDeviations(openDeviations)
                .inProgressDeviations(inProgressDeviations)
                .closedDeviations(closedDeviations)
                .criticalDeviations(criticalDeviations)
                .highDeviations(highDeviations)
                .build();
    }

    // ==========================================================
    // MATERIAL CONSUMPTION SUMMARY
    // ==========================================================

    @Override
    public List<MaterialConsumptionSummaryDTO>
            getMaterialConsumptionSummary() {

        return materialConsumptionRepository
                .getMaterialConsumptionSummary()
                .stream()
                .map(this::mapMaterialConsumption)
                .toList();
    }

    // ==========================================================
    // MATERIAL CONSUMPTION MAPPING
    // ==========================================================

    private MaterialConsumptionSummaryDTO mapMaterialConsumption(
            Object[] row) {

        return MaterialConsumptionSummaryDTO.builder()
                .materialId(
                        ((Number) row[0]).longValue())
                .materialName(
                        (String) row[1])
                .plannedQuantity(
                        ((Number) row[2]).doubleValue())
                .actualQuantity(
                        ((Number) row[3]).doubleValue())
                .unit(
                        (String) row[4])
                .build();
    }

}