package com.anandh.mes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.anandh.mes.entity.Deviation;
import com.anandh.mes.enums.DeviationSeverity;
import com.anandh.mes.enums.DeviationStatus;

public interface DeviationRepository
        extends JpaRepository<Deviation, Long> {

    List<Deviation> findByDeviationNumberIgnoreCase(
            String deviationNumber);

    List<Deviation> findByBatchBatchId(
            Long batchId);

    List<Deviation> findBySeverity(
            DeviationSeverity severity);

    List<Deviation> findByStatus(
            DeviationStatus status);

    List<Deviation> findByReportedByContainingIgnoreCase(
            String reportedBy);

    List<Deviation> findByBatchBatchIdAndStatus(
            Long batchId,
            DeviationStatus status);

    Page<Deviation> findAll(
            Pageable pageable);

}