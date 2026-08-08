package com.anandh.mes.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.anandh.mes.entity.AuditTrail;
import com.anandh.mes.enums.AuditAction;

public interface AuditTrailRepository
        extends JpaRepository<AuditTrail, Long> {

    // ==========================================================
    // SEARCH BY USER
    // ==========================================================

    List<AuditTrail> findByUsernameContainingIgnoreCase(
            String username);

    // ==========================================================
    // SEARCH BY ENTITY
    // ==========================================================

    List<AuditTrail> findByEntityNameIgnoreCase(
            String entityName);

    // ==========================================================
    // SEARCH BY ENTITY + ID
    // ==========================================================

    List<AuditTrail> findByEntityNameIgnoreCaseAndEntityId(
            String entityName,
            Long entityId);

    // ==========================================================
    // SEARCH BY ACTION
    // ==========================================================

    List<AuditTrail> findByAction(
            AuditAction action);

    // ==========================================================
    // SEARCH BY FIELD
    // ==========================================================

    List<AuditTrail> findByFieldNameContainingIgnoreCase(
            String fieldName);

    // ==========================================================
    // SEARCH BY USER + ACTION
    // ==========================================================

    List<AuditTrail> findByUsernameContainingIgnoreCaseAndAction(
            String username,
            AuditAction action);

    // ==========================================================
    // SEARCH BY DATE RANGE
    // ==========================================================

    List<AuditTrail> findByTimestampBetween(
            LocalDateTime startTime,
            LocalDateTime endTime);

    // ==========================================================
    // PAGINATION
    // ==========================================================

    Page<AuditTrail> findAll(
            Pageable pageable);
}