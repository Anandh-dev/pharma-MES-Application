package com.anandh.mes.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.anandh.mes.dto.MaterialConsumptionDTO;

public interface MaterialConsumptionService {

    // ==========================================================
    // CRUD
    // ==========================================================

    MaterialConsumptionDTO createMaterialConsumption(
            MaterialConsumptionDTO materialConsumptionDTO);

    List<MaterialConsumptionDTO> getAllMaterialConsumptions();

    MaterialConsumptionDTO getMaterialConsumptionById(
            Long id);

    MaterialConsumptionDTO updateMaterialConsumption(
            Long id,
            MaterialConsumptionDTO materialConsumptionDTO);

    void deleteMaterialConsumption(
            Long id);

    // ==========================================================
    // SEARCH
    // ==========================================================

    List<MaterialConsumptionDTO> getByBatch(
            Long batchId);

    List<MaterialConsumptionDTO> getByMaterial(
            Long materialId);

    List<MaterialConsumptionDTO> getByOperator(
            String operatorName);

    List<MaterialConsumptionDTO> getByBatchAndMaterial(
            Long batchId,
            Long materialId);

    Page<MaterialConsumptionDTO> getMaterialConsumptionPage(
            int page,
            int size,
            String sortBy);

    // ==========================================================
    // MATERIAL CONSUMPTION
    // ==========================================================

    MaterialConsumptionDTO recordConsumption(
            MaterialConsumptionDTO materialConsumptionDTO);

    Double getTotalMaterialConsumed(
            Long materialId);

    Double getTotalBatchConsumption(
            Long batchId);

}