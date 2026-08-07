package com.anandh.mes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.anandh.mes.entity.BatchRelease;
import com.anandh.mes.enums.BatchReleaseStatus;

public interface BatchReleaseRepository
        extends JpaRepository<BatchRelease, Long> {

    List<BatchRelease> findByBatchBatchId(
            Long batchId);

    List<BatchRelease> findByQualityInspectionQualityInspectionId(
            Long qualityInspectionId);

    List<BatchRelease> findByApprovedByContainingIgnoreCase(
            String approvedBy);

    List<BatchRelease> findByStatus(
            BatchReleaseStatus status);

    List<BatchRelease> findByBatchBatchIdAndStatus(
            Long batchId,
            BatchReleaseStatus status);

    Page<BatchRelease> findAll(
            Pageable pageable);

}