package com.anandh.mes.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.anandh.mes.dto.BatchReleaseDTO;
import com.anandh.mes.enums.BatchReleaseStatus;
import com.anandh.mes.service.BatchReleaseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/batch-releases")
@RequiredArgsConstructor
public class BatchReleaseController {

    private final BatchReleaseService batchReleaseService;

    // ==========================================================
    // CRUD
    // ==========================================================

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BatchReleaseDTO createBatchRelease(
            @Valid @RequestBody BatchReleaseDTO batchReleaseDTO) {

        return batchReleaseService.createBatchRelease(
                batchReleaseDTO);
    }

    @GetMapping
    public List<BatchReleaseDTO> getAllBatchReleases() {

        return batchReleaseService.getAllBatchReleases();
    }

    @GetMapping("/{id}")
    public BatchReleaseDTO getBatchReleaseById(
            @PathVariable Long id) {

        return batchReleaseService.getBatchReleaseById(id);
    }

    @PutMapping("/{id}")
    public BatchReleaseDTO updateBatchRelease(
            @PathVariable Long id,
            @Valid @RequestBody BatchReleaseDTO batchReleaseDTO) {

        return batchReleaseService.updateBatchRelease(
                id,
                batchReleaseDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBatchRelease(
            @PathVariable Long id) {

        batchReleaseService.deleteBatchRelease(id);
    }

    // ==========================================================
    // SEARCH
    // ==========================================================

    @GetMapping("/batch/{batchId}")
    public List<BatchReleaseDTO> getByBatch(
            @PathVariable Long batchId) {

        return batchReleaseService.getByBatch(batchId);
    }

    @GetMapping("/inspection/{qualityInspectionId}")
    public List<BatchReleaseDTO> getByQualityInspection(
            @PathVariable Long qualityInspectionId) {

        return batchReleaseService.getByQualityInspection(
                qualityInspectionId);
    }

    @GetMapping("/approver")
    public List<BatchReleaseDTO> getByApprover(
            @RequestParam String approvedBy) {

        return batchReleaseService.getByApprover(
                approvedBy);
    }

    @GetMapping("/status/{status}")
    public List<BatchReleaseDTO> getByStatus(
            @PathVariable BatchReleaseStatus status) {

        return batchReleaseService.getByStatus(status);
    }

    @GetMapping("/batch/{batchId}/status/{status}")
    public List<BatchReleaseDTO> getByBatchAndStatus(
            @PathVariable Long batchId,
            @PathVariable BatchReleaseStatus status) {

        return batchReleaseService.getByBatchAndStatus(
                batchId,
                status);
    }

    @GetMapping("/page")
    public Page<BatchReleaseDTO> getBatchReleasePage(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "batchReleaseId")
            String sortBy) {

        return batchReleaseService.getBatchReleasePage(
                page,
                size,
                sortBy);
    }

    // ==========================================================
    // RELEASE WORKFLOW
    // ==========================================================

    @PutMapping("/{id}/review")
    public BatchReleaseDTO startReview(
            @PathVariable Long id) {

        return batchReleaseService.startReview(id);
    }

    @PutMapping("/{id}/approve")
    public BatchReleaseDTO approveBatch(
            @PathVariable Long id) {

        return batchReleaseService.approveBatch(id);
    }

    @PutMapping("/{id}/release")
    public BatchReleaseDTO releaseBatch(
            @PathVariable Long id) {

        return batchReleaseService.releaseBatch(id);
    }

    @PutMapping("/{id}/reject")
    public BatchReleaseDTO rejectBatch(
            @PathVariable Long id) {

        return batchReleaseService.rejectBatch(id);
    }

    @PutMapping("/{id}/hold")
    public BatchReleaseDTO holdBatch(
            @PathVariable Long id) {

        return batchReleaseService.holdBatch(id);
    }

}