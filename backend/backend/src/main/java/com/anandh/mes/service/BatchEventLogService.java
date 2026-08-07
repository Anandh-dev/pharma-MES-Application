package com.anandh.mes.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.anandh.mes.dto.BatchEventLogDTO;
import com.anandh.mes.enums.BatchEventType;

public interface BatchEventLogService {

    // ==========================================================
    // CRUD
    // ==========================================================

    BatchEventLogDTO createBatchEventLog(
            BatchEventLogDTO batchEventLogDTO);

    List<BatchEventLogDTO> getAllBatchEventLogs();

    BatchEventLogDTO getBatchEventLogById(
            Long id);

    BatchEventLogDTO updateBatchEventLog(
            Long id,
            BatchEventLogDTO batchEventLogDTO);

    void deleteBatchEventLog(
            Long id);

    // ==========================================================
    // SEARCH
    // ==========================================================

    List<BatchEventLogDTO> getByBatch(
            Long batchId);

    List<BatchEventLogDTO> getByEventType(
            BatchEventType eventType);

    List<BatchEventLogDTO> getByOperator(
            String operatorName);

    List<BatchEventLogDTO> getByBatchAndEventType(
            Long batchId,
            BatchEventType eventType);

    Page<BatchEventLogDTO> getBatchEventLogPage(
            int page,
            int size,
            String sortBy);

    // ==========================================================
    // EVENT LOGGING
    // ==========================================================

    BatchEventLogDTO recordEvent(
            BatchEventLogDTO batchEventLogDTO);

    List<BatchEventLogDTO> getElectronicBatchRecord(
            Long batchId);

}