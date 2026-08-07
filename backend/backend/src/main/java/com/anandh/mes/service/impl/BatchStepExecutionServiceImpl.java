package com.anandh.mes.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anandh.mes.dto.BatchStepExecutionDTO;
import com.anandh.mes.entity.Batch;
import com.anandh.mes.entity.BatchStepExecution;
import com.anandh.mes.entity.RecipeStep;
import com.anandh.mes.enums.BatchStepStatus;
import com.anandh.mes.exception.ResourceNotFoundException;
import com.anandh.mes.repository.BatchRepository;
import com.anandh.mes.repository.BatchStepExecutionRepository;
import com.anandh.mes.repository.RecipeStepRepository;
import com.anandh.mes.service.BatchStepExecutionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BatchStepExecutionServiceImpl
        implements BatchStepExecutionService {

    private final BatchStepExecutionRepository batchStepExecutionRepository;

    private final BatchRepository batchRepository;

    private final RecipeStepRepository recipeStepRepository;

    // ==========================================================
    // CREATE
    // ==========================================================

    @Override
    public BatchStepExecutionDTO createBatchStepExecution(
            BatchStepExecutionDTO dto) {

        Batch batch = batchRepository.findById(dto.getBatchId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Batch not found"));

        RecipeStep recipeStep =
                recipeStepRepository.findById(dto.getRecipeStepId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Recipe Step not found"));

        BatchStepExecution execution = mapToEntity(dto);

        execution.setBatch(batch);
        execution.setRecipeStep(recipeStep);

        BatchStepExecution saved =
                batchStepExecutionRepository.save(execution);

        return mapToDTO(saved);
    }

    // ==========================================================
    // READ
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<BatchStepExecutionDTO> getAllBatchStepExecutions() {

        return batchStepExecutionRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BatchStepExecutionDTO getBatchStepExecutionById(Long id) {

        BatchStepExecution execution =
                batchStepExecutionRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Batch Step Execution not found"));

        return mapToDTO(execution);
    }

    // ==========================================================
    // UPDATE
    // ==========================================================

    @Override
    public BatchStepExecutionDTO updateBatchStepExecution(
            Long id,
            BatchStepExecutionDTO dto) {

        BatchStepExecution execution =
                batchStepExecutionRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Batch Step Execution not found"));

        Batch batch = batchRepository.findById(dto.getBatchId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Batch not found"));

        RecipeStep recipeStep =
                recipeStepRepository.findById(dto.getRecipeStepId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Recipe Step not found"));

        execution.setBatch(batch);
        execution.setRecipeStep(recipeStep);
        execution.setStepNumber(dto.getStepNumber());
        execution.setStatus(dto.getStatus());
        execution.setOperatorName(dto.getOperatorName());
        execution.setStartTime(dto.getStartTime());
        execution.setEndTime(dto.getEndTime());
        execution.setRemarks(dto.getRemarks());

        BatchStepExecution updated =
                batchStepExecutionRepository.save(execution);

        return mapToDTO(updated);
    }

    // ==========================================================
    // DELETE
    // ==========================================================

    @Override
    public void deleteBatchStepExecution(Long id) {

        BatchStepExecution execution =
                batchStepExecutionRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Batch Step Execution not found"));

        batchStepExecutionRepository.delete(execution);
    }

    // ==========================================================
    // SEARCH
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<BatchStepExecutionDTO> getByBatch(Long batchId) {

        return batchStepExecutionRepository
                .findByBatchBatchIdOrderByStepNumberAsc(batchId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BatchStepExecutionDTO> getByRecipeStep(
            Long recipeStepId) {

        return batchStepExecutionRepository
                .findByRecipeStepRecipeStepId(recipeStepId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BatchStepExecutionDTO> getByStatus(
            BatchStepStatus status) {

        return batchStepExecutionRepository
                .findByStatus(status)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BatchStepExecutionDTO> getByOperator(
            String operatorName) {

        return batchStepExecutionRepository
                .findByOperatorNameContainingIgnoreCase(operatorName)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // PAGINATION
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public Page<BatchStepExecutionDTO> getBatchStepExecutionPage(
            int page,
            int size,
            String sortBy) {

        Pageable pageable =
                PageRequest.of(page, size, Sort.by(sortBy));

        return batchStepExecutionRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    // ==========================================================
    // EXECUTION WORKFLOW
    // ==========================================================
    @Override
    public BatchStepExecutionDTO markReady(Long executionId) {

        BatchStepExecution execution = batchStepExecutionRepository.findById(executionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Batch Step Execution not found"));

        execution.setStatus(BatchStepStatus.READY);

        return mapToDTO(batchStepExecutionRepository.save(execution));
    }

    @Override
    public BatchStepExecutionDTO startExecution(Long executionId) {

        BatchStepExecution execution = batchStepExecutionRepository.findById(executionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Batch Step Execution not found"));

        execution.setStatus(BatchStepStatus.RUNNING);
        execution.setStartTime(java.time.LocalDateTime.now());

        return mapToDTO(batchStepExecutionRepository.save(execution));
    }

    @Override
    public BatchStepExecutionDTO completeExecution(Long executionId) {

        BatchStepExecution execution = batchStepExecutionRepository.findById(executionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Batch Step Execution not found"));

        execution.setStatus(BatchStepStatus.COMPLETED);
        execution.setEndTime(java.time.LocalDateTime.now());

        return mapToDTO(batchStepExecutionRepository.save(execution));
    }

    @Override
    public BatchStepExecutionDTO skipExecution(Long executionId) {

        BatchStepExecution execution = batchStepExecutionRepository.findById(executionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Batch Step Execution not found"));

        execution.setStatus(BatchStepStatus.SKIPPED);
        execution.setEndTime(java.time.LocalDateTime.now());

        return mapToDTO(batchStepExecutionRepository.save(execution));
    }

    @Override
    public BatchStepExecutionDTO assignOperator(
            Long executionId,
            String operatorName) {

        BatchStepExecution execution = batchStepExecutionRepository.findById(executionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Batch Step Execution not found"));

        execution.setOperatorName(operatorName);

        return mapToDTO(batchStepExecutionRepository.save(execution));
    }

    // ==========================================================
    // DTO MAPPING
    // ==========================================================

    private BatchStepExecutionDTO mapToDTO(
            BatchStepExecution execution) {

        return BatchStepExecutionDTO.builder()
                .batchStepExecutionId(execution.getBatchStepExecutionId())
                .batchId(execution.getBatch().getBatchId())
                .recipeStepId(execution.getRecipeStep().getRecipeStepId())
                .stepNumber(execution.getStepNumber())
                .status(execution.getStatus())
                .operatorName(execution.getOperatorName())
                .startTime(execution.getStartTime())
                .endTime(execution.getEndTime())
                .remarks(execution.getRemarks())
                .build();
    }

    private BatchStepExecution mapToEntity(
            BatchStepExecutionDTO dto) {

        return BatchStepExecution.builder()
                .stepNumber(dto.getStepNumber())
                .status(
                        dto.getStatus() == null
                                ? BatchStepStatus.WAITING
                                : dto.getStatus())
                .operatorName(dto.getOperatorName())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .remarks(dto.getRemarks())
                .build();
    }

}