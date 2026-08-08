package com.anandh.mes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anandh.mes.enums.InspectionStatus;
import com.anandh.mes.entity.QualityInspection;

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
    
    @Query("""
            SELECT COUNT(q)
            FROM QualityInspection q
            """)
    Long countTotalInspections();

    @Query("""
            SELECT COUNT(q)
            FROM QualityInspection q
            WHERE q.status = :status
            """)
    Long countInspectionsByStatus(
            @Param("status") InspectionStatus status);

}