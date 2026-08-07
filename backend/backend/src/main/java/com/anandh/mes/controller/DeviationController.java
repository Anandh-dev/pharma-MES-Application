package com.anandh.mes.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.anandh.mes.dto.DeviationDTO;
import com.anandh.mes.enums.DeviationSeverity;
import com.anandh.mes.enums.DeviationStatus;
import com.anandh.mes.service.DeviationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/deviations")
@RequiredArgsConstructor
public class DeviationController {

    private final DeviationService deviationService;

    // ==========================================================
    // CRUD
    // ==========================================================

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeviationDTO createDeviation(
            @Valid @RequestBody DeviationDTO deviationDTO) {

        return deviationService.createDeviation(
                deviationDTO);
    }

    @GetMapping
    public List<DeviationDTO> getAllDeviations() {

        return deviationService.getAllDeviations();
    }

    @GetMapping("/{id}")
    public DeviationDTO getDeviationById(
            @PathVariable Long id) {

        return deviationService.getDeviationById(id);
    }

    @PutMapping("/{id}")
    public DeviationDTO updateDeviation(
            @PathVariable Long id,
            @Valid @RequestBody DeviationDTO deviationDTO) {

        return deviationService.updateDeviation(
                id,
                deviationDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDeviation(
            @PathVariable Long id) {

        deviationService.deleteDeviation(id);
    }

    // ==========================================================
    // SEARCH
    // ==========================================================

    @GetMapping("/number")
    public List<DeviationDTO> getByDeviationNumber(
            @RequestParam String deviationNumber) {

        return deviationService.getByDeviationNumber(
                deviationNumber);
    }

    @GetMapping("/batch/{batchId}")
    public List<DeviationDTO> getByBatch(
            @PathVariable Long batchId) {

        return deviationService.getByBatch(batchId);
    }

    @GetMapping("/severity/{severity}")
    public List<DeviationDTO> getBySeverity(
            @PathVariable DeviationSeverity severity) {

        return deviationService.getBySeverity(severity);
    }

    @GetMapping("/status/{status}")
    public List<DeviationDTO> getByStatus(
            @PathVariable DeviationStatus status) {

        return deviationService.getByStatus(status);
    }

    @GetMapping("/reported-by")
    public List<DeviationDTO> getByReportedBy(
            @RequestParam String reportedBy) {

        return deviationService.getByReportedBy(
                reportedBy);
    }

    @GetMapping("/batch/{batchId}/status/{status}")
    public List<DeviationDTO> getByBatchAndStatus(
            @PathVariable Long batchId,
            @PathVariable DeviationStatus status) {

        return deviationService.getByBatchAndStatus(
                batchId,
                status);
    }

    @GetMapping("/page")
    public Page<DeviationDTO> getDeviationPage(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "deviationId")
            String sortBy) {

        return deviationService.getDeviationPage(
                page,
                size,
                sortBy);
    }

    // ==========================================================
    // DEVIATION WORKFLOW
    // ==========================================================

    @PutMapping("/{id}/investigate")
    public DeviationDTO startInvestigation(
            @PathVariable Long id) {

        return deviationService.startInvestigation(id);
    }

    @PutMapping("/{id}/close")
    public DeviationDTO closeDeviation(
            @PathVariable Long id) {

        return deviationService.closeDeviation(id);
    }

}