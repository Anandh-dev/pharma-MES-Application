package com.anandh.mes.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anandh.mes.dto.DeviationDTO;
import com.anandh.mes.entity.Batch;
import com.anandh.mes.entity.Deviation;
import com.anandh.mes.enums.DeviationSeverity;
import com.anandh.mes.enums.DeviationStatus;
import com.anandh.mes.exception.ResourceNotFoundException;
import com.anandh.mes.repository.BatchRepository;
import com.anandh.mes.repository.DeviationRepository;
import com.anandh.mes.service.DeviationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DeviationServiceImpl
        implements DeviationService {

    private final DeviationRepository deviationRepository;

    private final BatchRepository batchRepository;

    // ==========================================================
    // CRUD
    // ==========================================================

    @Override
    public DeviationDTO createDeviation(
            DeviationDTO dto) {

        Batch batch = batchRepository.findById(dto.getBatchId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Batch not found"));

        Deviation deviation = mapToEntity(dto);

        deviation.setBatch(batch);

        Deviation saved =
                deviationRepository.save(deviation);

        return mapToDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeviationDTO> getAllDeviations() {

        return deviationRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DeviationDTO getDeviationById(
            Long id) {

        Deviation deviation =
                deviationRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Deviation not found"));

        return mapToDTO(deviation);
    }

    @Override
    public DeviationDTO updateDeviation(
            Long id,
            DeviationDTO dto) {

        Deviation deviation =
                deviationRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Deviation not found"));

        Batch batch = batchRepository.findById(dto.getBatchId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Batch not found"));

        deviation.setDeviationNumber(dto.getDeviationNumber());
        deviation.setBatch(batch);
        deviation.setSeverity(dto.getSeverity());
        deviation.setDescription(dto.getDescription());
        deviation.setRootCause(dto.getRootCause());
        deviation.setCorrectiveAction(dto.getCorrectiveAction());
        deviation.setPreventiveAction(dto.getPreventiveAction());
        deviation.setStatus(dto.getStatus());
        deviation.setReportedBy(dto.getReportedBy());
        deviation.setReportedDate(dto.getReportedDate());

        Deviation updated =
                deviationRepository.save(deviation);

        return mapToDTO(updated);
    }

    @Override
    public void deleteDeviation(
            Long id) {

        Deviation deviation =
                deviationRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Deviation not found"));

        deviationRepository.delete(deviation);
    }

    // ==========================================================
    // SEARCH
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<DeviationDTO> getByDeviationNumber(
            String deviationNumber) {

        return deviationRepository
                .findByDeviationNumberIgnoreCase(deviationNumber)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeviationDTO> getByBatch(
            Long batchId) {

        return deviationRepository.findByBatchBatchId(batchId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeviationDTO> getBySeverity(
            DeviationSeverity severity) {

        return deviationRepository.findBySeverity(severity)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeviationDTO> getByStatus(
            DeviationStatus status) {

        return deviationRepository.findByStatus(status)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeviationDTO> getByReportedBy(
            String reportedBy) {

        return deviationRepository
                .findByReportedByContainingIgnoreCase(reportedBy)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeviationDTO> getByBatchAndStatus(
            Long batchId,
            DeviationStatus status) {

        return deviationRepository
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
    public Page<DeviationDTO> getDeviationPage(
            int page,
            int size,
            String sortBy) {

        Pageable pageable =
                PageRequest.of(page, size, Sort.by(sortBy));

        return deviationRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    // ==========================================================
    // DEVIATION WORKFLOW
    // ==========================================================
    @Override
    public DeviationDTO startInvestigation(
            Long deviationId) {

        Deviation deviation =
                deviationRepository.findById(deviationId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Deviation not found"));

        deviation.setStatus(DeviationStatus.IN_PROGRESS);

        return mapToDTO(
                deviationRepository.save(deviation));
    }

    @Override
    public DeviationDTO closeDeviation(
            Long deviationId) {

        Deviation deviation =
                deviationRepository.findById(deviationId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Deviation not found"));

        deviation.setStatus(DeviationStatus.CLOSED);

        return mapToDTO(
                deviationRepository.save(deviation));
    }

    // ==========================================================
    // DTO MAPPING
    // ==========================================================

    private DeviationDTO mapToDTO(
            Deviation deviation) {

        return DeviationDTO.builder()
                .deviationId(
                        deviation.getDeviationId())
                .deviationNumber(
                        deviation.getDeviationNumber())
                .batchId(
                        deviation.getBatch().getBatchId())
                .severity(
                        deviation.getSeverity())
                .description(
                        deviation.getDescription())
                .rootCause(
                        deviation.getRootCause())
                .correctiveAction(
                        deviation.getCorrectiveAction())
                .preventiveAction(
                        deviation.getPreventiveAction())
                .status(
                        deviation.getStatus())
                .reportedBy(
                        deviation.getReportedBy())
                .reportedDate(
                        deviation.getReportedDate())
                .build();
    }

    private Deviation mapToEntity(
            DeviationDTO dto) {

        return Deviation.builder()
                .deviationNumber(
                        dto.getDeviationNumber())
                .severity(
                        dto.getSeverity() == null
                                ? DeviationSeverity.LOW
                                : dto.getSeverity())
                .description(
                        dto.getDescription())
                .rootCause(
                        dto.getRootCause())
                .correctiveAction(
                        dto.getCorrectiveAction())
                .preventiveAction(
                        dto.getPreventiveAction())
                .status(
                        dto.getStatus() == null
                                ? DeviationStatus.OPEN
                                : dto.getStatus())
                .reportedBy(
                        dto.getReportedBy())
                .reportedDate(
                        dto.getReportedDate())
                .build();
    }

}