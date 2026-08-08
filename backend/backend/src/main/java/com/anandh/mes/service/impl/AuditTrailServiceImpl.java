package com.anandh.mes.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anandh.mes.dto.AuditTrailDTO;
import com.anandh.mes.entity.AuditTrail;
import com.anandh.mes.enums.AuditAction;
import com.anandh.mes.exception.ResourceNotFoundException;
import com.anandh.mes.repository.AuditTrailRepository;
import com.anandh.mes.service.AuditTrailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuditTrailServiceImpl
        implements AuditTrailService {

    private final AuditTrailRepository auditTrailRepository;

    // ==========================================================
    // CREATE AUDIT TRAIL
    // ==========================================================

    @Override
    public AuditTrailDTO createAuditTrail(
            AuditTrailDTO dto) {

        AuditTrail auditTrail = mapToEntity(dto);

        AuditTrail saved =
                auditTrailRepository.save(auditTrail);

        return mapToDTO(saved);
    }

    // ==========================================================
    // GET ALL
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<AuditTrailDTO> getAllAuditTrails() {

        return auditTrailRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // GET BY ID
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public AuditTrailDTO getAuditTrailById(
            Long id) {

        AuditTrail auditTrail =
                auditTrailRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Audit trail not found"));

        return mapToDTO(auditTrail);
    }

    // ==========================================================
    // SEARCH BY USERNAME
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<AuditTrailDTO> getByUsername(
            String username) {

        return auditTrailRepository
                .findByUsernameContainingIgnoreCase(username)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // SEARCH BY ENTITY
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<AuditTrailDTO> getByEntity(
            String entityName) {

        return auditTrailRepository
                .findByEntityNameIgnoreCase(entityName)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // SEARCH BY ENTITY + ID
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<AuditTrailDTO> getByEntityAndId(
            String entityName,
            Long entityId) {

        return auditTrailRepository
                .findByEntityNameIgnoreCaseAndEntityId(
                        entityName,
                        entityId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // SEARCH BY ACTION
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<AuditTrailDTO> getByAction(
            AuditAction action) {

        return auditTrailRepository
                .findByAction(action)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // SEARCH BY FIELD
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<AuditTrailDTO> getByFieldName(
            String fieldName) {

        return auditTrailRepository
                .findByFieldNameContainingIgnoreCase(fieldName)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // SEARCH BY USER + ACTION
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<AuditTrailDTO> getByUsernameAndAction(
            String username,
            AuditAction action) {

        return auditTrailRepository
                .findByUsernameContainingIgnoreCaseAndAction(
                        username,
                        action)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // SEARCH BY TIMESTAMP RANGE
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<AuditTrailDTO> getByTimestampBetween(
            LocalDateTime startTime,
            LocalDateTime endTime) {

        return auditTrailRepository
                .findByTimestampBetween(
                        startTime,
                        endTime)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // PAGINATION
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public Page<AuditTrailDTO> getAuditTrailPage(
            int page,
            int size,
            String sortBy) {

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by(sortBy));

        return auditTrailRepository
                .findAll(pageable)
                .map(this::mapToDTO);
    }

    // ==========================================================
    // DTO MAPPING
    // ==========================================================

    private AuditTrailDTO mapToDTO(
            AuditTrail auditTrail) {

        return AuditTrailDTO.builder()
                .auditTrailId(
                        auditTrail.getAuditTrailId())
                .action(
                        auditTrail.getAction())
                .username(
                        auditTrail.getUsername())
                .entityName(
                        auditTrail.getEntityName())
                .entityId(
                        auditTrail.getEntityId())
                .fieldName(
                        auditTrail.getFieldName())
                .oldValue(
                        auditTrail.getOldValue())
                .newValue(
                        auditTrail.getNewValue())
                .description(
                        auditTrail.getDescription())
                .timestamp(
                        auditTrail.getTimestamp())
                .ipAddress(
                        auditTrail.getIpAddress())
                .remarks(
                        auditTrail.getRemarks())
                .build();
    }

    private AuditTrail mapToEntity(
            AuditTrailDTO dto) {

        return AuditTrail.builder()
                .action(
                        dto.getAction())
                .username(
                        dto.getUsername())
                .entityName(
                        dto.getEntityName())
                .entityId(
                        dto.getEntityId())
                .fieldName(
                        dto.getFieldName())
                .oldValue(
                        dto.getOldValue())
                .newValue(
                        dto.getNewValue())
                .description(
                        dto.getDescription())
                .timestamp(
                        dto.getTimestamp())
                .ipAddress(
                        dto.getIpAddress())
                .remarks(
                        dto.getRemarks())
                .build();
    }

}