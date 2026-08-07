package com.anandh.mes.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.anandh.mes.dto.BatchDTO;
import com.anandh.mes.enums.BatchStatus;
import com.anandh.mes.service.BatchService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/batches")
@RequiredArgsConstructor
public class BatchController {

    private final BatchService batchService;

    // ==========================================================
    // CRUD
    // ==========================================================

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BatchDTO createBatch(
            @Valid @RequestBody BatchDTO batchDTO) {

        return batchService.createBatch(batchDTO);
    }

    @GetMapping
    public List<BatchDTO> getAllBatches() {

        return batchService.getAllBatches();
    }

    @GetMapping("/{id}")
    public BatchDTO getBatchById(
            @PathVariable Long id) {

        return batchService.getBatchById(id);
    }

    @PutMapping("/{id}")
    public BatchDTO updateBatch(
            @PathVariable Long id,
            @Valid @RequestBody BatchDTO batchDTO) {

        return batchService.updateBatch(id, batchDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBatch(
            @PathVariable Long id) {

        batchService.deleteBatch(id);
    }

    // ==========================================================
    // SEARCH
    // ==========================================================

    @GetMapping("/number/{batchNumber}")
    public BatchDTO getByBatchNumber(
            @PathVariable String batchNumber) {

        return batchService.getByBatchNumber(batchNumber);
    }

    @GetMapping("/status/{status}")
    public List<BatchDTO> getByStatus(
            @PathVariable BatchStatus status) {

        return batchService.getByStatus(status);
    }

    @GetMapping("/production-order/{productionOrderId}")
    public List<BatchDTO> getByProductionOrder(
            @PathVariable Long productionOrderId) {

        return batchService.getByProductionOrder(productionOrderId);
    }

    @GetMapping("/recipe/{recipeId}")
    public List<BatchDTO> getByRecipe(
            @PathVariable Long recipeId) {

        return batchService.getByRecipe(recipeId);
    }

    @GetMapping("/page")
    public Page<BatchDTO> getBatchPage(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "batchId")
            String sortBy) {

        return batchService.getBatchPage(
                page,
                size,
                sortBy);
    }

    // ==========================================================
    // BATCH WORKFLOW
    // ==========================================================

    @PutMapping("/{id}/ready")
    public BatchDTO markReady(
            @PathVariable Long id) {

        return batchService.markReady(id);
    }

    @PutMapping("/{id}/start")
    public BatchDTO startBatch(
            @PathVariable Long id) {

        return batchService.startBatch(id);
    }

    @PutMapping("/{id}/hold")
    public BatchDTO holdBatch(
            @PathVariable Long id) {

        return batchService.holdBatch(id);
    }

    @PutMapping("/{id}/resume")
    public BatchDTO resumeBatch(
            @PathVariable Long id) {

        return batchService.resumeBatch(id);
    }

    @PutMapping("/{id}/complete")
    public BatchDTO completeBatch(

            @PathVariable Long id,

            @RequestParam Double actualQuantity) {

        return batchService.completeBatch(
                id,
                actualQuantity);
    }

    @PutMapping("/{id}/close")
    public BatchDTO closeBatch(
            @PathVariable Long id) {

        return batchService.closeBatch(id);
    }

    @PutMapping("/{id}/cancel")
    public BatchDTO cancelBatch(
            @PathVariable Long id) {

        return batchService.cancelBatch(id);
    }

}