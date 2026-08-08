package com.anandh.mes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anandh.mes.enums.DeviationStatus;
import com.anandh.mes.enums.DeviationSeverity;
import com.anandh.mes.entity.Deviation;

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
    
    @Query("""
            SELECT COUNT(d)
            FROM Deviation d
            """)
    Long countTotalDeviations();

    @Query("""
            SELECT COUNT(d)
            FROM Deviation d
            WHERE d.status = :status
            """)
    Long countDeviationsByStatus(
            @Param("status") DeviationStatus status);

    @Query("""
            SELECT COUNT(d)
            FROM Deviation d
            WHERE d.severity = :severity
            """)
    Long countDeviationsBySeverity(
            @Param("severity") DeviationSeverity severity);

}