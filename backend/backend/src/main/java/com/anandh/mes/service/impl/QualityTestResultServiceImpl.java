package com.anandh.mes.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anandh.mes.dto.QualityTestResultDTO;
import com.anandh.mes.entity.QualityInspection;
import com.anandh.mes.entity.QualityTestResult;
import com.anandh.mes.exception.ResourceNotFoundException;
import com.anandh.mes.repository.QualityInspectionRepository;
import com.anandh.mes.repository.QualityTestResultRepository;
import com.anandh.mes.service.QualityTestResultService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class QualityTestResultServiceImpl
        implements QualityTestResultService {

    private final QualityTestResultRepository qualityTestResultRepository;

    private final QualityInspectionRepository qualityInspectionRepository;

    // ==========================================================
    // CREATE
    // ==========================================================

    @Override
    public QualityTestResultDTO createQualityTestResult(
            QualityTestResultDTO dto) {

        QualityInspection inspection =
                qualityInspectionRepository.findById(
                        dto.getQualityInspectionId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Quality Inspection not found"));

        QualityTestResult testResult = mapToEntity(dto);

        testResult.setQualityInspection(inspection);

        QualityTestResult saved =
                qualityTestResultRepository.save(testResult);

        return mapToDTO(saved);
    }

    // ==========================================================
    // READ
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<QualityTestResultDTO> getAllQualityTestResults() {

        return qualityTestResultRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public QualityTestResultDTO getQualityTestResultById(
            Long id) {

        QualityTestResult testResult =
                qualityTestResultRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Quality Test Result not found"));

        return mapToDTO(testResult);
    }

    // ==========================================================
    // UPDATE
    // ==========================================================

    @Override
    public QualityTestResultDTO updateQualityTestResult(
            Long id,
            QualityTestResultDTO dto) {

        QualityTestResult testResult =
                qualityTestResultRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Quality Test Result not found"));

        QualityInspection inspection =
                qualityInspectionRepository.findById(
                        dto.getQualityInspectionId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Quality Inspection not found"));

        testResult.setQualityInspection(inspection);
        testResult.setTestName(dto.getTestName());
        testResult.setExpectedValue(dto.getExpectedValue());
        testResult.setActualValue(dto.getActualValue());
        testResult.setUnit(dto.getUnit());
        testResult.setPassed(dto.getPassed());
        testResult.setRemarks(dto.getRemarks());

        QualityTestResult updated =
                qualityTestResultRepository.save(testResult);

        return mapToDTO(updated);
    }

    // ==========================================================
    // DELETE
    // ==========================================================

    @Override
    public void deleteQualityTestResult(Long id) {

        QualityTestResult testResult =
                qualityTestResultRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Quality Test Result not found"));

        qualityTestResultRepository.delete(testResult);
    }

    // ==========================================================
    // SEARCH
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<QualityTestResultDTO> getByInspection(
            Long qualityInspectionId) {

        return qualityTestResultRepository
                .findByQualityInspectionQualityInspectionId(
                        qualityInspectionId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<QualityTestResultDTO> getByTestName(
            String testName) {

        return qualityTestResultRepository
                .findByTestNameContainingIgnoreCase(testName)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<QualityTestResultDTO> getPassedTests() {

        return qualityTestResultRepository
                .findByPassed(true)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<QualityTestResultDTO> getFailedTests() {

        return qualityTestResultRepository
                .findByPassed(false)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<QualityTestResultDTO> getByInspectionAndResult(
            Long qualityInspectionId,
            Boolean passed) {

        return qualityTestResultRepository
                .findByQualityInspectionQualityInspectionIdAndPassed(
                        qualityInspectionId,
                        passed)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // PAGINATION
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public Page<QualityTestResultDTO> getQualityTestResultPage(
            int page,
            int size,
            String sortBy) {

        Pageable pageable =
                PageRequest.of(page, size, Sort.by(sortBy));

        return qualityTestResultRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    // ==========================================================
    // QUALITY TEST
    // ==========================================================
    @Override
    public QualityTestResultDTO markPassed(
            Long qualityTestResultId) {

        QualityTestResult testResult =
                qualityTestResultRepository.findById(qualityTestResultId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Quality Test Result not found"));

        testResult.setPassed(true);

        return mapToDTO(
                qualityTestResultRepository.save(testResult));
    }

    @Override
    public QualityTestResultDTO markFailed(
            Long qualityTestResultId) {

        QualityTestResult testResult =
                qualityTestResultRepository.findById(qualityTestResultId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Quality Test Result not found"));

        testResult.setPassed(false);

        return mapToDTO(
                qualityTestResultRepository.save(testResult));
    }

    // ==========================================================
    // DTO MAPPING
    // ==========================================================

    private QualityTestResultDTO mapToDTO(
            QualityTestResult testResult) {

        return QualityTestResultDTO.builder()
                .qualityTestResultId(
                        testResult.getQualityTestResultId())
                .qualityInspectionId(
                        testResult.getQualityInspection()
                                .getQualityInspectionId())
                .testName(
                        testResult.getTestName())
                .expectedValue(
                        testResult.getExpectedValue())
                .actualValue(
                        testResult.getActualValue())
                .unit(
                        testResult.getUnit())
                .passed(
                        testResult.getPassed())
                .remarks(
                        testResult.getRemarks())
                .build();
    }

    private QualityTestResult mapToEntity(
            QualityTestResultDTO dto) {

        return QualityTestResult.builder()
                .testName(
                        dto.getTestName())
                .expectedValue(
                        dto.getExpectedValue())
                .actualValue(
                        dto.getActualValue())
                .unit(
                        dto.getUnit())
                .passed(
                        dto.getPassed() == null
                                ? false
                                : dto.getPassed())
                .remarks(
                        dto.getRemarks())
                .build();
    }

}