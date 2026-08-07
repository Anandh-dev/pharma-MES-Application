package com.anandh.mes.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anandh.mes.dto.MaterialConsumptionDTO;
import com.anandh.mes.entity.Batch;
import com.anandh.mes.entity.Material;
import com.anandh.mes.entity.MaterialConsumption;
import com.anandh.mes.exception.ResourceNotFoundException;
import com.anandh.mes.repository.BatchRepository;
import com.anandh.mes.repository.MaterialConsumptionRepository;
import com.anandh.mes.repository.MaterialRepository;
import com.anandh.mes.service.MaterialConsumptionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MaterialConsumptionServiceImpl
        implements MaterialConsumptionService {

    private final MaterialConsumptionRepository materialConsumptionRepository;

    private final BatchRepository batchRepository;

    private final MaterialRepository materialRepository;

    // ==========================================================
    // CREATE
    // ==========================================================

    @Override
    public MaterialConsumptionDTO createMaterialConsumption(
            MaterialConsumptionDTO dto) {

        Batch batch = batchRepository.findById(dto.getBatchId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Batch not found"));

        Material material = materialRepository.findById(dto.getMaterialId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Material not found"));

        MaterialConsumption consumption = mapToEntity(dto);

        consumption.setBatch(batch);
        consumption.setMaterial(material);

        MaterialConsumption saved =
                materialConsumptionRepository.save(consumption);

        return mapToDTO(saved);
    }

    // ==========================================================
    // READ
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<MaterialConsumptionDTO> getAllMaterialConsumptions() {

        return materialConsumptionRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MaterialConsumptionDTO getMaterialConsumptionById(Long id) {

        MaterialConsumption consumption =
                materialConsumptionRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Material Consumption not found"));

        return mapToDTO(consumption);
    }

    // ==========================================================
    // UPDATE
    // ==========================================================

    @Override
    public MaterialConsumptionDTO updateMaterialConsumption(
            Long id,
            MaterialConsumptionDTO dto) {

        MaterialConsumption consumption =
                materialConsumptionRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Material Consumption not found"));

        Batch batch = batchRepository.findById(dto.getBatchId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Batch not found"));

        Material material = materialRepository.findById(dto.getMaterialId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Material not found"));

        consumption.setBatch(batch);
        consumption.setMaterial(material);
        consumption.setPlannedQuantity(dto.getPlannedQuantity());
        consumption.setActualQuantity(dto.getActualQuantity());
        consumption.setUnit(dto.getUnit());
        consumption.setConsumptionTime(dto.getConsumptionTime());
        consumption.setOperatorName(dto.getOperatorName());
        consumption.setRemarks(dto.getRemarks());

        MaterialConsumption updated =
                materialConsumptionRepository.save(consumption);

        return mapToDTO(updated);
    }

    // ==========================================================
    // DELETE
    // ==========================================================

    @Override
    public void deleteMaterialConsumption(Long id) {

        MaterialConsumption consumption =
                materialConsumptionRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Material Consumption not found"));

        materialConsumptionRepository.delete(consumption);
    }

    // ==========================================================
    // SEARCH
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<MaterialConsumptionDTO> getByBatch(Long batchId) {

        return materialConsumptionRepository.findByBatchBatchId(batchId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaterialConsumptionDTO> getByMaterial(Long materialId) {

        return materialConsumptionRepository.findByMaterialMaterialId(materialId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaterialConsumptionDTO> getByOperator(String operatorName) {

        return materialConsumptionRepository
                .findByOperatorNameContainingIgnoreCase(operatorName)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaterialConsumptionDTO> getByBatchAndMaterial(
            Long batchId,
            Long materialId) {

        return materialConsumptionRepository
                .findByBatchBatchIdAndMaterialMaterialId(batchId, materialId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // PAGINATION
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public Page<MaterialConsumptionDTO> getMaterialConsumptionPage(
            int page,
            int size,
            String sortBy) {

        Pageable pageable =
                PageRequest.of(page, size, Sort.by(sortBy));

        return materialConsumptionRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    // ==========================================================
    // MATERIAL CONSUMPTION
    // ==========================================================
    @Override
    public MaterialConsumptionDTO recordConsumption(
            MaterialConsumptionDTO dto) {

        return createMaterialConsumption(dto);
    }

    @Override
    @Transactional(readOnly = true)
    public Double getTotalMaterialConsumed(Long materialId) {

        return materialConsumptionRepository
                .findByMaterialMaterialId(materialId)
                .stream()
                .mapToDouble(MaterialConsumption::getActualQuantity)
                .sum();
    }

    @Override
    @Transactional(readOnly = true)
    public Double getTotalBatchConsumption(Long batchId) {

        return materialConsumptionRepository
                .findByBatchBatchId(batchId)
                .stream()
                .mapToDouble(MaterialConsumption::getActualQuantity)
                .sum();
    }

    // ==========================================================
    // DTO MAPPING
    // ==========================================================

    private MaterialConsumptionDTO mapToDTO(
            MaterialConsumption consumption) {

        return MaterialConsumptionDTO.builder()
                .materialConsumptionId(
                        consumption.getMaterialConsumptionId())
                .batchId(consumption.getBatch().getBatchId())
                .materialId(consumption.getMaterial().getMaterialId())
                .plannedQuantity(consumption.getPlannedQuantity())
                .actualQuantity(consumption.getActualQuantity())
                .unit(consumption.getUnit())
                .consumptionTime(consumption.getConsumptionTime())
                .operatorName(consumption.getOperatorName())
                .remarks(consumption.getRemarks())
                .build();
    }

    private MaterialConsumption mapToEntity(
            MaterialConsumptionDTO dto) {

        return MaterialConsumption.builder()
                .plannedQuantity(dto.getPlannedQuantity())
                .actualQuantity(dto.getActualQuantity())
                .unit(dto.getUnit())
                .consumptionTime(dto.getConsumptionTime())
                .operatorName(dto.getOperatorName())
                .remarks(dto.getRemarks())
                .build();
    }

}