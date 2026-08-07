package com.anandh.mes.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.anandh.mes.dto.QualityInspectionDTO;
import com.anandh.mes.enums.InspectionStatus;

public interface QualityInspectionService {

    // ==========================================================
    // CRUD
    // ==========================================================

    QualityInspectionDTO createQualityInspection(
            QualityInspectionDTO qualityInspectionDTO);

    List<QualityInspectionDTO> getAllQualityInspections();

    QualityInspectionDTO getQualityInspectionById(
            Long id);

    QualityInspectionDTO updateQualityInspection(
            Long id,
            QualityInspectionDTO qualityInspectionDTO);

    void deleteQualityInspection(
            Long id);

    // ==========================================================
    // SEARCH
    // ==========================================================

    List<QualityInspectionDTO> getByBatch(
            Long batchId);

    List<QualityInspectionDTO> getByInspector(
            String inspectorName);

    List<QualityInspectionDTO> getByStatus(
            InspectionStatus status);

    List<QualityInspectionDTO> getByBatchAndStatus(
            Long batchId,
            InspectionStatus status);

    Page<QualityInspectionDTO> getQualityInspectionPage(
            int page,
            int size,
            String sortBy);

    // ==========================================================
    // INSPECTION WORKFLOW
    // ==========================================================

    QualityInspectionDTO startInspection(
            Long inspectionId);

    QualityInspectionDTO markPassed(
            Long inspectionId);

    QualityInspectionDTO markFailed(
            Long inspectionId);

    QualityInspectionDTO markRetestRequired(
            Long inspectionId);

}