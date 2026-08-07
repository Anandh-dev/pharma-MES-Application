package com.anandh.mes.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anandh.mes.dto.ProcessParameterLogDTO;
import com.anandh.mes.entity.Batch;
import com.anandh.mes.entity.ProcessParameterLog;
import com.anandh.mes.exception.ResourceNotFoundException;
import com.anandh.mes.repository.BatchRepository;
import com.anandh.mes.repository.ProcessParameterLogRepository;
import com.anandh.mes.service.ProcessParameterLogService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProcessParameterLogServiceImpl
        implements ProcessParameterLogService {

    private final ProcessParameterLogRepository processParameterLogRepository;

    private final BatchRepository batchRepository;

    // ==========================================================
    // CRUD
    // ==========================================================

    @Override
    public ProcessParameterLogDTO createProcessParameterLog(
            ProcessParameterLogDTO dto) {

        Batch batch = batchRepository.findById(dto.getBatchId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Batch not found"));

        ProcessParameterLog log = mapToEntity(dto);

        log.setBatch(batch);

        ProcessParameterLog saved =
                processParameterLogRepository.save(log);

        return mapToDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProcessParameterLogDTO> getAllProcessParameterLogs() {

        return processParameterLogRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProcessParameterLogDTO getProcessParameterLogById(
            Long id) {

        ProcessParameterLog log =
                processParameterLogRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Process Parameter Log not found"));

        return mapToDTO(log);
    }

    @Override
    public ProcessParameterLogDTO updateProcessParameterLog(
            Long id,
            ProcessParameterLogDTO dto) {

        ProcessParameterLog log =
                processParameterLogRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Process Parameter Log not found"));

        Batch batch = batchRepository.findById(dto.getBatchId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Batch not found"));

        log.setBatch(batch);
        log.setParameterName(dto.getParameterName());
        log.setParameterValue(dto.getParameterValue());
        log.setUnit(dto.getUnit());
        log.setRecordedBy(dto.getRecordedBy());
        log.setRecordedTime(dto.getRecordedTime());
        log.setRemarks(dto.getRemarks());

        ProcessParameterLog updated =
                processParameterLogRepository.save(log);

        return mapToDTO(updated);
    }

    @Override
    public void deleteProcessParameterLog(
            Long id) {

        ProcessParameterLog log =
                processParameterLogRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Process Parameter Log not found"));

        processParameterLogRepository.delete(log);
    }

    // ==========================================================
    // SEARCH
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<ProcessParameterLogDTO> getByBatch(
            Long batchId) {

        return processParameterLogRepository
                .findByBatchBatchId(batchId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProcessParameterLogDTO> getByParameterName(
            String parameterName) {

        return processParameterLogRepository
                .findByParameterNameContainingIgnoreCase(parameterName)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProcessParameterLogDTO> getByRecordedBy(
            String recordedBy) {

        return processParameterLogRepository
                .findByRecordedByContainingIgnoreCase(recordedBy)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProcessParameterLogDTO> getByBatchAndParameter(
            Long batchId,
            String parameterName) {

        return processParameterLogRepository
                .findByBatchBatchIdAndParameterNameContainingIgnoreCase(
                        batchId,
                        parameterName)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // PAGINATION
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public Page<ProcessParameterLogDTO> getProcessParameterLogPage(
            int page,
            int size,
            String sortBy) {

        Pageable pageable =
                PageRequest.of(page, size, Sort.by(sortBy));

        return processParameterLogRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    // ==========================================================
    // PROCESS MONITORING
    // ==========================================================
    @Override
    public ProcessParameterLogDTO recordParameter(
            ProcessParameterLogDTO dto) {

        return createProcessParameterLog(dto);
    }

    // ==========================================================
    // DTO MAPPING
    // ==========================================================

    private ProcessParameterLogDTO mapToDTO(
            ProcessParameterLog log) {

        return ProcessParameterLogDTO.builder()
                .processParameterLogId(
                        log.getProcessParameterLogId())
                .batchId(
                        log.getBatch().getBatchId())
                .parameterName(
                        log.getParameterName())
                .parameterValue(
                        log.getParameterValue())
                .unit(
                        log.getUnit())
                .recordedBy(
                        log.getRecordedBy())
                .recordedTime(
                        log.getRecordedTime())
                .remarks(
                        log.getRemarks())
                .build();
    }

    private ProcessParameterLog mapToEntity(
            ProcessParameterLogDTO dto) {

        return ProcessParameterLog.builder()
                .parameterName(
                        dto.getParameterName())
                .parameterValue(
                        dto.getParameterValue())
                .unit(
                        dto.getUnit())
                .recordedBy(
                        dto.getRecordedBy())
                .recordedTime(
                        dto.getRecordedTime())
                .remarks(
                        dto.getRemarks())
                .build();
    }

}