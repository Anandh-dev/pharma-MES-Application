package com.anandh.mes.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.anandh.mes.dto.QualityTestResultDTO;

public interface QualityTestResultService {

    // ==========================================================
    // CRUD
    // ==========================================================

    QualityTestResultDTO createQualityTestResult(
            QualityTestResultDTO qualityTestResultDTO);

    List<QualityTestResultDTO> getAllQualityTestResults();

    QualityTestResultDTO getQualityTestResultById(
            Long id);

    QualityTestResultDTO updateQualityTestResult(
            Long id,
            QualityTestResultDTO qualityTestResultDTO);

    void deleteQualityTestResult(
            Long id);

    // ==========================================================
    // SEARCH
    // ==========================================================

    List<QualityTestResultDTO> getByInspection(
            Long qualityInspectionId);

    List<QualityTestResultDTO> getByTestName(
            String testName);

    List<QualityTestResultDTO> getPassedTests();

    List<QualityTestResultDTO> getFailedTests();

    List<QualityTestResultDTO> getByInspectionAndResult(
            Long qualityInspectionId,
            Boolean passed);

    Page<QualityTestResultDTO> getQualityTestResultPage(
            int page,
            int size,
            String sortBy);

    // ==========================================================
    // QUALITY TEST
    // ==========================================================

    QualityTestResultDTO markPassed(
            Long qualityTestResultId);

    QualityTestResultDTO markFailed(
            Long qualityTestResultId);

}