package com.anandh.mes.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.anandh.mes.dto.QualityInspectionDTO;
import com.anandh.mes.enums.InspectionStatus;
import com.anandh.mes.service.QualityInspectionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/quality-inspections")
@RequiredArgsConstructor
public class QualityInspectionController {

    private final QualityInspectionService qualityInspectionService;

    // ==========================================================
    // CRUD
    // ==========================================================

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QualityInspectionDTO createQualityInspection(
            @Valid @RequestBody QualityInspectionDTO qualityInspectionDTO) {

        return qualityInspectionService.createQualityInspection(
                qualityInspectionDTO);
    }

    @GetMapping
    public List<QualityInspectionDTO> getAllQualityInspections() {

        return qualityInspectionService.getAllQualityInspections();
    }

    @GetMapping("/{id}")
    public QualityInspectionDTO getQualityInspectionById(
            @PathVariable Long id) {

        return qualityInspectionService.getQualityInspectionById(id);
    }

    @PutMapping("/{id}")
    public QualityInspectionDTO updateQualityInspection(
            @PathVariable Long id,
            @Valid @RequestBody QualityInspectionDTO qualityInspectionDTO) {

        return qualityInspectionService.updateQualityInspection(
                id,
                qualityInspectionDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQualityInspection(
            @PathVariable Long id) {

        qualityInspectionService.deleteQualityInspection(id);
    }

    // ==========================================================
    // SEARCH
    // ==========================================================

    @GetMapping("/batch/{batchId}")
    public List<QualityInspectionDTO> getByBatch(
            @PathVariable Long batchId) {

        return qualityInspectionService.getByBatch(batchId);
    }

    @GetMapping("/inspector")
    public List<QualityInspectionDTO> getByInspector(
            @RequestParam String inspectorName) {

        return qualityInspectionService.getByInspector(inspectorName);
    }

    @GetMapping("/status/{status}")
    public List<QualityInspectionDTO> getByStatus(
            @PathVariable InspectionStatus status) {

        return qualityInspectionService.getByStatus(status);
    }

    @GetMapping("/batch/{batchId}/status/{status}")
    public List<QualityInspectionDTO> getByBatchAndStatus(
            @PathVariable Long batchId,
            @PathVariable InspectionStatus status) {

        return qualityInspectionService.getByBatchAndStatus(
                batchId,
                status);
    }

    @GetMapping("/page")
    public Page<QualityInspectionDTO> getQualityInspectionPage(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "qualityInspectionId")
            String sortBy) {

        return qualityInspectionService.getQualityInspectionPage(
                page,
                size,
                sortBy);
    }

    // ==========================================================
    // INSPECTION WORKFLOW
    // ==========================================================

    @PutMapping("/{id}/start")
    public QualityInspectionDTO startInspection(
            @PathVariable Long id) {

        return qualityInspectionService.startInspection(id);
    }

    @PutMapping("/{id}/pass")
    public QualityInspectionDTO markPassed(
            @PathVariable Long id) {

        return qualityInspectionService.markPassed(id);
    }

    @PutMapping("/{id}/fail")
    public QualityInspectionDTO markFailed(
            @PathVariable Long id) {

        return qualityInspectionService.markFailed(id);
    }

    @PutMapping("/{id}/retest")
    public QualityInspectionDTO markRetestRequired(
            @PathVariable Long id) {

        return qualityInspectionService.markRetestRequired(id);
    }

}