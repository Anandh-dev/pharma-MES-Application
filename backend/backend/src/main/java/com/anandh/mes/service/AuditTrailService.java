package com.anandh.mes.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;

import com.anandh.mes.dto.AuditTrailDTO;
import com.anandh.mes.enums.AuditAction;

public interface AuditTrailService {

    // ==========================================================
    // CREATE
    // ==========================================================

    AuditTrailDTO createAuditTrail(
            AuditTrailDTO dto);

    // ==========================================================
    // READ
    // ==========================================================

    List<AuditTrailDTO> getAllAuditTrails();

    AuditTrailDTO getAuditTrailById(
            Long id);

    // ==========================================================
    // SEARCH
    // ==========================================================

    List<AuditTrailDTO> getByUsername(
            String username);

    List<AuditTrailDTO> getByEntity(
            String entityName);

    List<AuditTrailDTO> getByEntityAndId(
            String entityName,
            Long entityId);

    List<AuditTrailDTO> getByAction(
            AuditAction action);

    List<AuditTrailDTO> getByFieldName(
            String fieldName);

    List<AuditTrailDTO> getByUsernameAndAction(
            String username,
            AuditAction action);

    List<AuditTrailDTO> getByTimestampBetween(
            LocalDateTime startTime,
            LocalDateTime endTime);

    // ==========================================================
    // PAGINATION
    // ==========================================================

    Page<AuditTrailDTO> getAuditTrailPage(
            int page,
            int size,
            String sortBy);

}