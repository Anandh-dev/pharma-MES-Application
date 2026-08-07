package com.anandh.mes.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.anandh.mes.dto.QualityTestResultDTO;
import com.anandh.mes.service.QualityTestResultService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/quality-test-results")
@RequiredArgsConstructor
public class QualityTestResultController {

    private final QualityTestResultService qualityTestResultService;

    // ==========================================================
    // CRUD
    // ==========================================================

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QualityTestResultDTO createQualityTestResult(
            @Valid @RequestBody QualityTestResultDTO qualityTestResultDTO) {

        return qualityTestResultService.createQualityTestResult(
                qualityTestResultDTO);
    }

    @GetMapping
    public List<QualityTestResultDTO> getAllQualityTestResults() {

        return qualityTestResultService.getAllQualityTestResults();
    }

    @GetMapping("/{id}")
    public QualityTestResultDTO getQualityTestResultById(
            @PathVariable Long id) {

        return qualityTestResultService.getQualityTestResultById(id);
    }

    @PutMapping("/{id}")
    public QualityTestResultDTO updateQualityTestResult(
            @PathVariable Long id,
            @Valid @RequestBody QualityTestResultDTO qualityTestResultDTO) {

        return qualityTestResultService.updateQualityTestResult(
                id,
                qualityTestResultDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQualityTestResult(
            @PathVariable Long id) {

        qualityTestResultService.deleteQualityTestResult(id);
    }

    // ==========================================================
    // SEARCH
    // ==========================================================

    @GetMapping("/inspection/{qualityInspectionId}")
    public List<QualityTestResultDTO> getByInspection(
            @PathVariable Long qualityInspectionId) {

        return qualityTestResultService.getByInspection(
                qualityInspectionId);
    }

    @GetMapping("/test-name")
    public List<QualityTestResultDTO> getByTestName(
            @RequestParam String testName) {

        return qualityTestResultService.getByTestName(
                testName);
    }

    @GetMapping("/passed")
    public List<QualityTestResultDTO> getPassedTests() {

        return qualityTestResultService.getPassedTests();
    }

    @GetMapping("/failed")
    public List<QualityTestResultDTO> getFailedTests() {

        return qualityTestResultService.getFailedTests();
    }

    @GetMapping("/inspection/{qualityInspectionId}/result")
    public List<QualityTestResultDTO> getByInspectionAndResult(
            @PathVariable Long qualityInspectionId,
            @RequestParam Boolean passed) {

        return qualityTestResultService.getByInspectionAndResult(
                qualityInspectionId,
                passed);
    }

    @GetMapping("/page")
    public Page<QualityTestResultDTO> getQualityTestResultPage(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "qualityTestResultId")
            String sortBy) {

        return qualityTestResultService.getQualityTestResultPage(
                page,
                size,
                sortBy);
    }

    // ==========================================================
    // QUALITY TEST WORKFLOW
    // ==========================================================

    @PutMapping("/{id}/pass")
    public QualityTestResultDTO markPassed(
            @PathVariable Long id) {

        return qualityTestResultService.markPassed(id);
    }

    @PutMapping("/{id}/fail")
    public QualityTestResultDTO markFailed(
            @PathVariable Long id) {

        return qualityTestResultService.markFailed(id);
    }

}