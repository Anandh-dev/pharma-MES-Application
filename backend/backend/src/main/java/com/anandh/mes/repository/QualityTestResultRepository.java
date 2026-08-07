package com.anandh.mes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.anandh.mes.entity.QualityTestResult;

public interface QualityTestResultRepository
        extends JpaRepository<QualityTestResult, Long> {

    List<QualityTestResult> findByQualityInspectionQualityInspectionId(
            Long qualityInspectionId);

    List<QualityTestResult> findByTestNameContainingIgnoreCase(
            String testName);

    List<QualityTestResult> findByPassed(
            Boolean passed);

    List<QualityTestResult> findByQualityInspectionQualityInspectionIdAndPassed(
            Long qualityInspectionId,
            Boolean passed);

    Page<QualityTestResult> findAll(
            Pageable pageable);

}