package com.anandh.mes.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.anandh.mes.dto.AuditTrailDTO;
import com.anandh.mes.enums.AuditAction;
import com.anandh.mes.service.AuditTrailService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/audit-trails")
@RequiredArgsConstructor
public class AuditTrailController {

    private final AuditTrailService auditTrailService;

    // ==========================================================
    // CREATE
    // ==========================================================

    @PostMapping
    public AuditTrailDTO createAuditTrail(
            @Valid @RequestBody AuditTrailDTO dto) {

        return auditTrailService.createAuditTrail(dto);
    }

    // ==========================================================
    // GET ALL
    // ==========================================================

    @GetMapping
    public List<AuditTrailDTO> getAllAuditTrails() {

        return auditTrailService.getAllAuditTrails();
    }

    // ==========================================================
    // GET BY ID
    // ==========================================================

    @GetMapping("/{id}")
    public AuditTrailDTO getAuditTrailById(
            @PathVariable Long id) {

        return auditTrailService.getAuditTrailById(id);
    }

    // ==========================================================
    // SEARCH BY USERNAME
    // ==========================================================

    @GetMapping("/username")
    public List<AuditTrailDTO> getByUsername(
            @RequestParam String username) {

        return auditTrailService.getByUsername(username);
    }

    // ==========================================================
    // SEARCH BY ENTITY
    // ==========================================================

    @GetMapping("/entity")
    public List<AuditTrailDTO> getByEntity(
            @RequestParam String entityName) {

        return auditTrailService.getByEntity(entityName);
    }

    // ==========================================================
    // SEARCH BY ENTITY + ID
    // ==========================================================

    @GetMapping("/entity/{entityName}/{entityId}")
    public List<AuditTrailDTO> getByEntityAndId(
            @PathVariable String entityName,
            @PathVariable Long entityId) {

        return auditTrailService.getByEntityAndId(
                entityName,
                entityId);
    }

    // ==========================================================
    // SEARCH BY ACTION
    // ==========================================================

    @GetMapping("/action/{action}")
    public List<AuditTrailDTO> getByAction(
            @PathVariable AuditAction action) {

        return auditTrailService.getByAction(action);
    }

    // ==========================================================
    // SEARCH BY FIELD
    // ==========================================================

    @GetMapping("/field")
    public List<AuditTrailDTO> getByFieldName(
            @RequestParam String fieldName) {

        return auditTrailService.getByFieldName(fieldName);
    }

    // ==========================================================
    // SEARCH BY USER + ACTION
    // ==========================================================

    @GetMapping("/username/{username}/action/{action}")
    public List<AuditTrailDTO> getByUsernameAndAction(
            @PathVariable String username,
            @PathVariable AuditAction action) {

        return auditTrailService.getByUsernameAndAction(
                username,
                action);
    }

    // ==========================================================
    // SEARCH BY TIME RANGE
    // ==========================================================

    @GetMapping("/timestamp")
    public List<AuditTrailDTO> getByTimestampBetween(
            @RequestParam LocalDateTime startTime,
            @RequestParam LocalDateTime endTime) {

        return auditTrailService.getByTimestampBetween(
                startTime,
                endTime);
    }

    // ==========================================================
    // PAGINATION
    // ==========================================================

    @GetMapping("/page")
    public org.springframework.data.domain.Page<AuditTrailDTO>
            getAuditTrailPage(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "auditTrailId")
            String sortBy) {

        return auditTrailService.getAuditTrailPage(
                page,
                size,
                sortBy);
    }

}