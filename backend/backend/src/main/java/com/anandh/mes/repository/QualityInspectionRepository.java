package com.anandh.mes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.anandh.mes.entity.QualityInspection;
import com.anandh.mes.enums.InspectionStatus;

public interface QualityInspectionRepository
        extends JpaRepository<QualityInspection, Long> {

    List<QualityInspection> findByBatchBatchId(
            Long batchId);

    List<QualityInspection> findByInspectorNameContainingIgnoreCase(
            String inspectorName);

    List<QualityInspection> findByStatus(
            InspectionStatus status);

    List<QualityInspection> findByBatchBatchIdAndStatus(
            Long batchId,
            InspectionStatus status);

    Page<QualityInspection> findAll(
            Pageable pageable);

}