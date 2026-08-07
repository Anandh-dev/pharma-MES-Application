package com.anandh.mes.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anandh.mes.dto.BatchEventLogDTO;
import com.anandh.mes.entity.Batch;
import com.anandh.mes.entity.BatchEventLog;
import com.anandh.mes.enums.BatchEventType;
import com.anandh.mes.exception.ResourceNotFoundException;
import com.anandh.mes.repository.BatchEventLogRepository;
import com.anandh.mes.repository.BatchRepository;
import com.anandh.mes.service.BatchEventLogService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BatchEventLogServiceImpl
        implements BatchEventLogService {

    private final BatchEventLogRepository batchEventLogRepository;

    private final BatchRepository batchRepository;

    // ==========================================================
    // CREATE
    // ==========================================================

    @Override
    public BatchEventLogDTO createBatchEventLog(
            BatchEventLogDTO dto) {

        Batch batch = batchRepository.findById(dto.getBatchId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Batch not found"));

        BatchEventLog eventLog = mapToEntity(dto);

        eventLog.setBatch(batch);

        BatchEventLog saved =
                batchEventLogRepository.save(eventLog);

        return mapToDTO(saved);
    }

    // ==========================================================
    // READ
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<BatchEventLogDTO> getAllBatchEventLogs() {

        return batchEventLogRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BatchEventLogDTO getBatchEventLogById(Long id) {

        BatchEventLog eventLog =
                batchEventLogRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Batch Event Log not found"));

        return mapToDTO(eventLog);
    }

    // ==========================================================
    // UPDATE
    // ==========================================================

    @Override
    public BatchEventLogDTO updateBatchEventLog(
            Long id,
            BatchEventLogDTO dto) {

        BatchEventLog eventLog =
                batchEventLogRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Batch Event Log not found"));

        Batch batch = batchRepository.findById(dto.getBatchId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Batch not found"));

        eventLog.setBatch(batch);
        eventLog.setEventType(dto.getEventType());
        eventLog.setEventDescription(dto.getEventDescription());
        eventLog.setOperatorName(dto.getOperatorName());
        eventLog.setEventTime(dto.getEventTime());
        eventLog.setRemarks(dto.getRemarks());

        BatchEventLog updated =
                batchEventLogRepository.save(eventLog);

        return mapToDTO(updated);
    }

    // ==========================================================
    // DELETE
    // ==========================================================

    @Override
    public void deleteBatchEventLog(Long id) {

        BatchEventLog eventLog =
                batchEventLogRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Batch Event Log not found"));

        batchEventLogRepository.delete(eventLog);
    }

    // ==========================================================
    // SEARCH
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<BatchEventLogDTO> getByBatch(Long batchId) {

        return batchEventLogRepository
                .findByBatchBatchIdOrderByEventTimeAsc(batchId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BatchEventLogDTO> getByEventType(
            BatchEventType eventType) {

        return batchEventLogRepository
                .findByEventType(eventType)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BatchEventLogDTO> getByOperator(
            String operatorName) {

        return batchEventLogRepository
                .findByOperatorNameContainingIgnoreCase(operatorName)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BatchEventLogDTO> getByBatchAndEventType(
            Long batchId,
            BatchEventType eventType) {

        return batchEventLogRepository
                .findByBatchBatchIdAndEventType(batchId, eventType)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // PAGINATION
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public Page<BatchEventLogDTO> getBatchEventLogPage(
            int page,
            int size,
            String sortBy) {

        Pageable pageable =
                PageRequest.of(page, size, Sort.by(sortBy));

        return batchEventLogRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    // ==========================================================
    // EVENT LOGGING
    // ==========================================================
    @Override
    public BatchEventLogDTO recordEvent(
            BatchEventLogDTO dto) {

        return createBatchEventLog(dto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BatchEventLogDTO> getElectronicBatchRecord(
            Long batchId) {

        return batchEventLogRepository
                .findByBatchBatchIdOrderByEventTimeAsc(batchId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // DTO MAPPING
    // ==========================================================

    private BatchEventLogDTO mapToDTO(
            BatchEventLog eventLog) {

        return BatchEventLogDTO.builder()
                .batchEventLogId(
                        eventLog.getBatchEventLogId())
                .batchId(
                        eventLog.getBatch().getBatchId())
                .eventType(
                        eventLog.getEventType())
                .eventDescription(
                        eventLog.getEventDescription())
                .operatorName(
                        eventLog.getOperatorName())
                .eventTime(
                        eventLog.getEventTime())
                .remarks(
                        eventLog.getRemarks())
                .build();
    }

    private BatchEventLog mapToEntity(
            BatchEventLogDTO dto) {

        return BatchEventLog.builder()
                .eventType(
                        dto.getEventType())
                .eventDescription(
                        dto.getEventDescription())
                .operatorName(
                        dto.getOperatorName())
                .eventTime(
                        dto.getEventTime())
                .remarks(
                        dto.getRemarks())
                .build();
    }

}