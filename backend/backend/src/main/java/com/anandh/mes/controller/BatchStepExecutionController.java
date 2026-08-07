package com.anandh.mes.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.anandh.mes.dto.BatchStepExecutionDTO;
import com.anandh.mes.enums.BatchStepStatus;
import com.anandh.mes.service.BatchStepExecutionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/batch-step-executions")
@RequiredArgsConstructor
public class BatchStepExecutionController {

    private final BatchStepExecutionService batchStepExecutionService;

    // ==========================================================
    // CRUD
    // ==========================================================

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BatchStepExecutionDTO createBatchStepExecution(
            @Valid @RequestBody BatchStepExecutionDTO batchStepExecutionDTO) {

        return batchStepExecutionService.createBatchStepExecution(
                batchStepExecutionDTO);
    }

    @GetMapping
    public List<BatchStepExecutionDTO> getAllBatchStepExecutions() {

        return batchStepExecutionService.getAllBatchStepExecutions();
    }

    @GetMapping("/{id}")
    public BatchStepExecutionDTO getBatchStepExecutionById(
            @PathVariable Long id) {

        return batchStepExecutionService.getBatchStepExecutionById(id);
    }

    @PutMapping("/{id}")
    public BatchStepExecutionDTO updateBatchStepExecution(
            @PathVariable Long id,
            @Valid @RequestBody BatchStepExecutionDTO batchStepExecutionDTO) {

        return batchStepExecutionService.updateBatchStepExecution(
                id,
                batchStepExecutionDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBatchStepExecution(
            @PathVariable Long id) {

        batchStepExecutionService.deleteBatchStepExecution(id);
    }

    // ==========================================================
    // SEARCH
    // ==========================================================

    @GetMapping("/batch/{batchId}")
    public List<BatchStepExecutionDTO> getByBatch(
            @PathVariable Long batchId) {

        return batchStepExecutionService.getByBatch(batchId);
    }

    @GetMapping("/recipe-step/{recipeStepId}")
    public List<BatchStepExecutionDTO> getByRecipeStep(
            @PathVariable Long recipeStepId) {

        return batchStepExecutionService.getByRecipeStep(recipeStepId);
    }

    @GetMapping("/status/{status}")
    public List<BatchStepExecutionDTO> getByStatus(
            @PathVariable BatchStepStatus status) {

        return batchStepExecutionService.getByStatus(status);
    }

    @GetMapping("/operator")
    public List<BatchStepExecutionDTO> getByOperator(
            @RequestParam String operatorName) {

        return batchStepExecutionService.getByOperator(operatorName);
    }

    @GetMapping("/page")
    public Page<BatchStepExecutionDTO> getBatchStepExecutionPage(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "batchStepExecutionId")
            String sortBy) {

        return batchStepExecutionService.getBatchStepExecutionPage(
                page,
                size,
                sortBy);
    }

    // ==========================================================
    // EXECUTION WORKFLOW
    // ==========================================================

    @PutMapping("/{id}/ready")
    public BatchStepExecutionDTO markReady(
            @PathVariable Long id) {

        return batchStepExecutionService.markReady(id);
    }

    @PutMapping("/{id}/start")
    public BatchStepExecutionDTO startExecution(
            @PathVariable Long id) {

        return batchStepExecutionService.startExecution(id);
    }

    @PutMapping("/{id}/complete")
    public BatchStepExecutionDTO completeExecution(
            @PathVariable Long id) {

        return batchStepExecutionService.completeExecution(id);
    }

    @PutMapping("/{id}/skip")
    public BatchStepExecutionDTO skipExecution(
            @PathVariable Long id) {

        return batchStepExecutionService.skipExecution(id);
    }

    @PutMapping("/{id}/assign-operator")
    public BatchStepExecutionDTO assignOperator(

            @PathVariable Long id,

            @RequestParam String operatorName) {

        return batchStepExecutionService.assignOperator(
                id,
                operatorName);
    }

}