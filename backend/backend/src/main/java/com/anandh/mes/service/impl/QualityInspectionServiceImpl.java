package com.anandh.mes.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anandh.mes.dto.QualityInspectionDTO;
import com.anandh.mes.entity.Batch;
import com.anandh.mes.entity.QualityInspection;
import com.anandh.mes.enums.InspectionStatus;
import com.anandh.mes.exception.ResourceNotFoundException;
import com.anandh.mes.repository.BatchRepository;
import com.anandh.mes.repository.QualityInspectionRepository;
import com.anandh.mes.service.QualityInspectionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class QualityInspectionServiceImpl
        implements QualityInspectionService {

    private final QualityInspectionRepository qualityInspectionRepository;

    private final BatchRepository batchRepository;

    // ==========================================================
    // CREATE
    // ==========================================================

    @Override
    public QualityInspectionDTO createQualityInspection(
            QualityInspectionDTO dto) {

        Batch batch = batchRepository.findById(dto.getBatchId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Batch not found"));

        QualityInspection inspection = mapToEntity(dto);

        inspection.setBatch(batch);

        QualityInspection saved =
                qualityInspectionRepository.save(inspection);

        return mapToDTO(saved);
    }

    // ==========================================================
    // READ
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<QualityInspectionDTO> getAllQualityInspections() {

        return qualityInspectionRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public QualityInspectionDTO getQualityInspectionById(
            Long id) {

        QualityInspection inspection =
                qualityInspectionRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Quality Inspection not found"));

        return mapToDTO(inspection);
    }

    // ==========================================================
    // UPDATE
    // ==========================================================

    @Override
    public QualityInspectionDTO updateQualityInspection(
            Long id,
            QualityInspectionDTO dto) {

        QualityInspection inspection =
                qualityInspectionRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Quality Inspection not found"));

        Batch batch = batchRepository.findById(dto.getBatchId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Batch not found"));

        inspection.setBatch(batch);
        inspection.setInspectorName(dto.getInspectorName());
        inspection.setInspectionDate(dto.getInspectionDate());
        inspection.setStatus(dto.getStatus());
        inspection.setRemarks(dto.getRemarks());

        QualityInspection updated =
                qualityInspectionRepository.save(inspection);

        return mapToDTO(updated);
    }

    // ==========================================================
    // DELETE
    // ==========================================================

    @Override
    public void deleteQualityInspection(Long id) {

        QualityInspection inspection =
                qualityInspectionRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Quality Inspection not found"));

        qualityInspectionRepository.delete(inspection);
    }

    // ==========================================================
    // SEARCH
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<QualityInspectionDTO> getByBatch(Long batchId) {

        return qualityInspectionRepository.findByBatchBatchId(batchId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<QualityInspectionDTO> getByInspector(
            String inspectorName) {

        return qualityInspectionRepository
                .findByInspectorNameContainingIgnoreCase(inspectorName)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<QualityInspectionDTO> getByStatus(
            InspectionStatus status) {

        return qualityInspectionRepository
                .findByStatus(status)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<QualityInspectionDTO> getByBatchAndStatus(
            Long batchId,
            InspectionStatus status) {

        return qualityInspectionRepository
                .findByBatchBatchIdAndStatus(batchId, status)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // PAGINATION
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public Page<QualityInspectionDTO> getQualityInspectionPage(
            int page,
            int size,
            String sortBy) {

        Pageable pageable =
                PageRequest.of(page, size, Sort.by(sortBy));

        return qualityInspectionRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    // ==========================================================
    // INSPECTION WORKFLOW
    // ==========================================================
    @Override
    public QualityInspectionDTO startInspection(
            Long inspectionId) {

        QualityInspection inspection =
                qualityInspectionRepository.findById(inspectionId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Quality Inspection not found"));

        inspection.setStatus(InspectionStatus.IN_PROGRESS);

        return mapToDTO(
                qualityInspectionRepository.save(inspection));
    }

    @Override
    public QualityInspectionDTO markPassed(
            Long inspectionId) {

        QualityInspection inspection =
                qualityInspectionRepository.findById(inspectionId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Quality Inspection not found"));

        inspection.setStatus(InspectionStatus.PASSED);

        return mapToDTO(
                qualityInspectionRepository.save(inspection));
    }

    @Override
    public QualityInspectionDTO markFailed(
            Long inspectionId) {

        QualityInspection inspection =
                qualityInspectionRepository.findById(inspectionId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Quality Inspection not found"));

        inspection.setStatus(InspectionStatus.FAILED);

        return mapToDTO(
                qualityInspectionRepository.save(inspection));
    }

    @Override
    public QualityInspectionDTO markRetestRequired(
            Long inspectionId) {

        QualityInspection inspection =
                qualityInspectionRepository.findById(inspectionId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Quality Inspection not found"));

        inspection.setStatus(InspectionStatus.RETEST_REQUIRED);

        return mapToDTO(
                qualityInspectionRepository.save(inspection));
    }

    // ==========================================================
    // DTO MAPPING
    // ==========================================================

    private QualityInspectionDTO mapToDTO(
            QualityInspection inspection) {

        return QualityInspectionDTO.builder()
                .qualityInspectionId(
                        inspection.getQualityInspectionId())
                .batchId(
                        inspection.getBatch().getBatchId())
                .inspectorName(
                        inspection.getInspectorName())
                .inspectionDate(
                        inspection.getInspectionDate())
                .status(
                        inspection.getStatus())
                .remarks(
                        inspection.getRemarks())
                .build();
    }

    private QualityInspection mapToEntity(
            QualityInspectionDTO dto) {

        return QualityInspection.builder()
                .inspectorName(
                        dto.getInspectorName())
                .inspectionDate(
                        dto.getInspectionDate())
                .status(
                        dto.getStatus() == null
                                ? InspectionStatus.PENDING
                                : dto.getStatus())
                .remarks(
                        dto.getRemarks())
                .build();
    }

}