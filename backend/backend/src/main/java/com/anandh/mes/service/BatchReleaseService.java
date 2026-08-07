package com.anandh.mes.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.anandh.mes.dto.BatchReleaseDTO;
import com.anandh.mes.enums.BatchReleaseStatus;

public interface BatchReleaseService {

    // ==========================================================
    // CRUD
    // ==========================================================

    BatchReleaseDTO createBatchRelease(
            BatchReleaseDTO batchReleaseDTO);

    List<BatchReleaseDTO> getAllBatchReleases();

    BatchReleaseDTO getBatchReleaseById(
            Long id);

    BatchReleaseDTO updateBatchRelease(
            Long id,
            BatchReleaseDTO batchReleaseDTO);

    void deleteBatchRelease(
            Long id);

    // ==========================================================
    // SEARCH
    // ==========================================================

    List<BatchReleaseDTO> getByBatch(
            Long batchId);

    List<BatchReleaseDTO> getByQualityInspection(
            Long qualityInspectionId);

    List<BatchReleaseDTO> getByApprover(
            String approvedBy);

    List<BatchReleaseDTO> getByStatus(
            BatchReleaseStatus status);

    List<BatchReleaseDTO> getByBatchAndStatus(
            Long batchId,
            BatchReleaseStatus status);

    Page<BatchReleaseDTO> getBatchReleasePage(
            int page,
            int size,
            String sortBy);

    // ==========================================================
    // RELEASE WORKFLOW
    // ==========================================================

    BatchReleaseDTO startReview(
            Long batchReleaseId);

    BatchReleaseDTO approveBatch(
            Long batchReleaseId);

    BatchReleaseDTO releaseBatch(
            Long batchReleaseId);

    BatchReleaseDTO rejectBatch(
            Long batchReleaseId);

    BatchReleaseDTO holdBatch(
            Long batchReleaseId);

}