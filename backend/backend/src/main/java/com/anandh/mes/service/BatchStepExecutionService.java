package com.anandh.mes.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.anandh.mes.dto.BatchStepExecutionDTO;
import com.anandh.mes.enums.BatchStepStatus;

public interface BatchStepExecutionService {

    // ==========================================================
    // CRUD
    // ==========================================================

    BatchStepExecutionDTO createBatchStepExecution(
            BatchStepExecutionDTO batchStepExecutionDTO);

    List<BatchStepExecutionDTO> getAllBatchStepExecutions();

    BatchStepExecutionDTO getBatchStepExecutionById(
            Long id);

    BatchStepExecutionDTO updateBatchStepExecution(
            Long id,
            BatchStepExecutionDTO batchStepExecutionDTO);

    void deleteBatchStepExecution(
            Long id);

    // ==========================================================
    // SEARCH
    // ==========================================================

    List<BatchStepExecutionDTO> getByBatch(
            Long batchId);

    List<BatchStepExecutionDTO> getByRecipeStep(
            Long recipeStepId);

    List<BatchStepExecutionDTO> getByStatus(
            BatchStepStatus status);

    List<BatchStepExecutionDTO> getByOperator(
            String operatorName);

    Page<BatchStepExecutionDTO> getBatchStepExecutionPage(
            int page,
            int size,
            String sortBy);

    // ==========================================================
    // EXECUTION WORKFLOW
    // ==========================================================

    BatchStepExecutionDTO markReady(
            Long executionId);

    BatchStepExecutionDTO startExecution(
            Long executionId);

    BatchStepExecutionDTO completeExecution(
            Long executionId);

    BatchStepExecutionDTO skipExecution(
            Long executionId);

    BatchStepExecutionDTO assignOperator(
            Long executionId,
            String operatorName);

}