package com.anandh.mes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.anandh.mes.entity.MaterialConsumption;

public interface MaterialConsumptionRepository
        extends JpaRepository<MaterialConsumption, Long> {

    List<MaterialConsumption> findByBatchBatchId(
            Long batchId);

    List<MaterialConsumption> findByMaterialMaterialId(
            Long materialId);

    List<MaterialConsumption> findByOperatorNameContainingIgnoreCase(
            String operatorName);

    List<MaterialConsumption> findByBatchBatchIdAndMaterialMaterialId(
            Long batchId,
            Long materialId);

    Page<MaterialConsumption> findAll(
            Pageable pageable);

}