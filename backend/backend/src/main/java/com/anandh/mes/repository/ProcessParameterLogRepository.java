package com.anandh.mes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.anandh.mes.entity.ProcessParameterLog;

public interface ProcessParameterLogRepository
        extends JpaRepository<ProcessParameterLog, Long> {

    List<ProcessParameterLog> findByBatchBatchId(
            Long batchId);

    List<ProcessParameterLog> findByParameterNameContainingIgnoreCase(
            String parameterName);

    List<ProcessParameterLog> findByRecordedByContainingIgnoreCase(
            String recordedBy);

    List<ProcessParameterLog> findByBatchBatchIdAndParameterNameContainingIgnoreCase(
            Long batchId,
            String parameterName);

    Page<ProcessParameterLog> findAll(
            Pageable pageable);

}