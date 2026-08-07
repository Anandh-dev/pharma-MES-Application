package com.anandh.mes.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anandh.mes.dto.BatchReleaseDTO;
import com.anandh.mes.entity.Batch;
import com.anandh.mes.entity.BatchRelease;
import com.anandh.mes.entity.QualityInspection;
import com.anandh.mes.enums.BatchReleaseStatus;
import com.anandh.mes.exception.ResourceNotFoundException;
import com.anandh.mes.repository.BatchReleaseRepository;
import com.anandh.mes.repository.BatchRepository;
import com.anandh.mes.repository.QualityInspectionRepository;
import com.anandh.mes.service.BatchReleaseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BatchReleaseServiceImpl
        implements BatchReleaseService {

    private final BatchReleaseRepository batchReleaseRepository;

    private final BatchRepository batchRepository;

    private final QualityInspectionRepository qualityInspectionRepository;

    // ==========================================================
    // CREATE
    // ==========================================================

    @Override
    public BatchReleaseDTO createBatchRelease(
            BatchReleaseDTO dto) {

        Batch batch = batchRepository.findById(dto.getBatchId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Batch not found"));

        QualityInspection inspection =
                qualityInspectionRepository.findById(
                        dto.getQualityInspectionId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Quality Inspection not found"));

        BatchRelease batchRelease = mapToEntity(dto);

        batchRelease.setBatch(batch);
        batchRelease.setQualityInspection(inspection);

        BatchRelease saved =
                batchReleaseRepository.save(batchRelease);

        return mapToDTO(saved);
    }

    // ==========================================================
    // READ
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<BatchReleaseDTO> getAllBatchReleases() {

        return batchReleaseRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BatchReleaseDTO getBatchReleaseById(
            Long id) {

        BatchRelease batchRelease =
                batchReleaseRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Batch Release not found"));

        return mapToDTO(batchRelease);
    }

    // ==========================================================
    // UPDATE
    // ==========================================================

    @Override
    public BatchReleaseDTO updateBatchRelease(
            Long id,
            BatchReleaseDTO dto) {

        BatchRelease batchRelease =
                batchReleaseRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Batch Release not found"));

        Batch batch = batchRepository.findById(dto.getBatchId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Batch not found"));

        QualityInspection inspection =
                qualityInspectionRepository.findById(
                        dto.getQualityInspectionId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Quality Inspection not found"));

        batchRelease.setBatch(batch);
        batchRelease.setQualityInspection(inspection);
        batchRelease.setApprovedBy(dto.getApprovedBy());
        batchRelease.setReleaseDate(dto.getReleaseDate());
        batchRelease.setStatus(dto.getStatus());
        batchRelease.setRemarks(dto.getRemarks());

        BatchRelease updated =
                batchReleaseRepository.save(batchRelease);

        return mapToDTO(updated);
    }

    // ==========================================================
    // DELETE
    // ==========================================================

    @Override
    public void deleteBatchRelease(Long id) {

        BatchRelease batchRelease =
                batchReleaseRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Batch Release not found"));

        batchReleaseRepository.delete(batchRelease);
    }

    // ==========================================================
    // SEARCH
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<BatchReleaseDTO> getByBatch(
            Long batchId) {

        return batchReleaseRepository.findByBatchBatchId(batchId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BatchReleaseDTO> getByQualityInspection(
            Long qualityInspectionId) {

        return batchReleaseRepository
                .findByQualityInspectionQualityInspectionId(
                        qualityInspectionId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BatchReleaseDTO> getByApprover(
            String approvedBy) {

        return batchReleaseRepository
                .findByApprovedByContainingIgnoreCase(
                        approvedBy)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BatchReleaseDTO> getByStatus(
            BatchReleaseStatus status) {

        return batchReleaseRepository
                .findByStatus(status)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BatchReleaseDTO> getByBatchAndStatus(
            Long batchId,
            BatchReleaseStatus status) {

        return batchReleaseRepository
                .findByBatchBatchIdAndStatus(
                        batchId,
                        status)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // PAGINATION
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public Page<BatchReleaseDTO> getBatchReleasePage(
            int page,
            int size,
            String sortBy) {

        Pageable pageable =
                PageRequest.of(page, size, Sort.by(sortBy));

        return batchReleaseRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    // ==========================================================
    // RELEASE WORKFLOW
    // ==========================================================
    @Override
    public BatchReleaseDTO startReview(
            Long batchReleaseId) {

        BatchRelease batchRelease =
                batchReleaseRepository.findById(batchReleaseId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Batch Release not found"));

        batchRelease.setStatus(BatchReleaseStatus.UNDER_REVIEW);

        return mapToDTO(
                batchReleaseRepository.save(batchRelease));
    }

    @Override
    public BatchReleaseDTO approveBatch(
            Long batchReleaseId) {

        BatchRelease batchRelease =
                batchReleaseRepository.findById(batchReleaseId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Batch Release not found"));

        batchRelease.setStatus(BatchReleaseStatus.APPROVED);

        return mapToDTO(
                batchReleaseRepository.save(batchRelease));
    }

    @Override
    public BatchReleaseDTO releaseBatch(
            Long batchReleaseId) {

        BatchRelease batchRelease =
                batchReleaseRepository.findById(batchReleaseId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Batch Release not found"));

        batchRelease.setStatus(BatchReleaseStatus.RELEASED);

        return mapToDTO(
                batchReleaseRepository.save(batchRelease));
    }

    @Override
    public BatchReleaseDTO rejectBatch(
            Long batchReleaseId) {

        BatchRelease batchRelease =
                batchReleaseRepository.findById(batchReleaseId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Batch Release not found"));

        batchRelease.setStatus(BatchReleaseStatus.REJECTED);

        return mapToDTO(
                batchReleaseRepository.save(batchRelease));
    }

    @Override
    public BatchReleaseDTO holdBatch(
            Long batchReleaseId) {

        BatchRelease batchRelease =
                batchReleaseRepository.findById(batchReleaseId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Batch Release not found"));

        batchRelease.setStatus(BatchReleaseStatus.ON_HOLD);

        return mapToDTO(
                batchReleaseRepository.save(batchRelease));
    }

    // ==========================================================
    // DTO MAPPING
    // ==========================================================

    private BatchReleaseDTO mapToDTO(
            BatchRelease batchRelease) {

        return BatchReleaseDTO.builder()
                .batchReleaseId(
                        batchRelease.getBatchReleaseId())
                .batchId(
                        batchRelease.getBatch().getBatchId())
                .qualityInspectionId(
                        batchRelease.getQualityInspection()
                                .getQualityInspectionId())
                .approvedBy(
                        batchRelease.getApprovedBy())
                .releaseDate(
                        batchRelease.getReleaseDate())
                .status(
                        batchRelease.getStatus())
                .remarks(
                        batchRelease.getRemarks())
                .build();
    }

    private BatchRelease mapToEntity(
            BatchReleaseDTO dto) {

        return BatchRelease.builder()
                .approvedBy(
                        dto.getApprovedBy())
                .releaseDate(
                        dto.getReleaseDate())
                .status(
                        dto.getStatus() == null
                                ? BatchReleaseStatus.PENDING
                                : dto.getStatus())
                .remarks(
                        dto.getRemarks())
                .build();
    }

}