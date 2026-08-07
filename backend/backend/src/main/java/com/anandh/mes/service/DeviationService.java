package com.anandh.mes.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.anandh.mes.dto.DeviationDTO;
import com.anandh.mes.enums.DeviationSeverity;
import com.anandh.mes.enums.DeviationStatus;

public interface DeviationService {

    // ==========================================================
    // CRUD
    // ==========================================================

    DeviationDTO createDeviation(
            DeviationDTO deviationDTO);

    List<DeviationDTO> getAllDeviations();

    DeviationDTO getDeviationById(
            Long id);

    DeviationDTO updateDeviation(
            Long id,
            DeviationDTO deviationDTO);

    void deleteDeviation(
            Long id);

    // ==========================================================
    // SEARCH
    // ==========================================================

    List<DeviationDTO> getByDeviationNumber(
            String deviationNumber);

    List<DeviationDTO> getByBatch(
            Long batchId);

    List<DeviationDTO> getBySeverity(
            DeviationSeverity severity);

    List<DeviationDTO> getByStatus(
            DeviationStatus status);

    List<DeviationDTO> getByReportedBy(
            String reportedBy);

    List<DeviationDTO> getByBatchAndStatus(
            Long batchId,
            DeviationStatus status);

    Page<DeviationDTO> getDeviationPage(
            int page,
            int size,
            String sortBy);

    // ==========================================================
    // DEVIATION WORKFLOW
    // ==========================================================

    DeviationDTO startInvestigation(
            Long deviationId);

    DeviationDTO closeDeviation(
            Long deviationId);

}