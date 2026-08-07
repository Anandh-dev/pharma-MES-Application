package com.anandh.mes.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.anandh.mes.dto.ProcessParameterLogDTO;

public interface ProcessParameterLogService {

    // ==========================================================
    // CRUD
    // ==========================================================

    ProcessParameterLogDTO createProcessParameterLog(
            ProcessParameterLogDTO processParameterLogDTO);

    List<ProcessParameterLogDTO> getAllProcessParameterLogs();

    ProcessParameterLogDTO getProcessParameterLogById(
            Long id);

    ProcessParameterLogDTO updateProcessParameterLog(
            Long id,
            ProcessParameterLogDTO processParameterLogDTO);

    void deleteProcessParameterLog(
            Long id);

    // ==========================================================
    // SEARCH
    // ==========================================================

    List<ProcessParameterLogDTO> getByBatch(
            Long batchId);

    List<ProcessParameterLogDTO> getByParameterName(
            String parameterName);

    List<ProcessParameterLogDTO> getByRecordedBy(
            String recordedBy);

    List<ProcessParameterLogDTO> getByBatchAndParameter(
            Long batchId,
            String parameterName);

    Page<ProcessParameterLogDTO> getProcessParameterLogPage(
            int page,
            int size,
            String sortBy);

    // ==========================================================
    // PROCESS MONITORING
    // ==========================================================

    ProcessParameterLogDTO recordParameter(
            ProcessParameterLogDTO processParameterLogDTO);

}