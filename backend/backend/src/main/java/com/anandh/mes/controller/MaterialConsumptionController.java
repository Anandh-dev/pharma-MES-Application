package com.anandh.mes.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.anandh.mes.dto.MaterialConsumptionDTO;
import com.anandh.mes.service.MaterialConsumptionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/material-consumptions")
@RequiredArgsConstructor
public class MaterialConsumptionController {

    private final MaterialConsumptionService materialConsumptionService;

    // ==========================================================
    // CRUD
    // ==========================================================

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MaterialConsumptionDTO createMaterialConsumption(
            @Valid @RequestBody MaterialConsumptionDTO materialConsumptionDTO) {

        return materialConsumptionService.createMaterialConsumption(
                materialConsumptionDTO);
    }

    @GetMapping
    public List<MaterialConsumptionDTO> getAllMaterialConsumptions() {

        return materialConsumptionService.getAllMaterialConsumptions();
    }

    @GetMapping("/{id}")
    public MaterialConsumptionDTO getMaterialConsumptionById(
            @PathVariable Long id) {

        return materialConsumptionService.getMaterialConsumptionById(id);
    }

    @PutMapping("/{id}")
    public MaterialConsumptionDTO updateMaterialConsumption(
            @PathVariable Long id,
            @Valid @RequestBody MaterialConsumptionDTO materialConsumptionDTO) {

        return materialConsumptionService.updateMaterialConsumption(
                id,
                materialConsumptionDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMaterialConsumption(
            @PathVariable Long id) {

        materialConsumptionService.deleteMaterialConsumption(id);
    }

    // ==========================================================
    // SEARCH
    // ==========================================================

    @GetMapping("/batch/{batchId}")
    public List<MaterialConsumptionDTO> getByBatch(
            @PathVariable Long batchId) {

        return materialConsumptionService.getByBatch(batchId);
    }

    @GetMapping("/material/{materialId}")
    public List<MaterialConsumptionDTO> getByMaterial(
            @PathVariable Long materialId) {

        return materialConsumptionService.getByMaterial(materialId);
    }

    @GetMapping("/operator")
    public List<MaterialConsumptionDTO> getByOperator(
            @RequestParam String operatorName) {

        return materialConsumptionService.getByOperator(operatorName);
    }

    @GetMapping("/batch/{batchId}/material/{materialId}")
    public List<MaterialConsumptionDTO> getByBatchAndMaterial(
            @PathVariable Long batchId,
            @PathVariable Long materialId) {

        return materialConsumptionService.getByBatchAndMaterial(
                batchId,
                materialId);
    }

    @GetMapping("/page")
    public Page<MaterialConsumptionDTO> getMaterialConsumptionPage(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "materialConsumptionId")
            String sortBy) {

        return materialConsumptionService.getMaterialConsumptionPage(
                page,
                size,
                sortBy);
    }

    // ==========================================================
    // MATERIAL CONSUMPTION
    // ==========================================================

    @PostMapping("/record")
    public MaterialConsumptionDTO recordConsumption(
            @Valid @RequestBody MaterialConsumptionDTO materialConsumptionDTO) {

        return materialConsumptionService.recordConsumption(
                materialConsumptionDTO);
    }

    @GetMapping("/material/{materialId}/total")
    public Double getTotalMaterialConsumed(
            @PathVariable Long materialId) {

        return materialConsumptionService.getTotalMaterialConsumed(
                materialId);
    }

    @GetMapping("/batch/{batchId}/total")
    public Double getTotalBatchConsumption(
            @PathVariable Long batchId) {

        return materialConsumptionService.getTotalBatchConsumption(
                batchId);
    }

}