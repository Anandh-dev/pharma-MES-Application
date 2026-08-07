package com.anandh.mes.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anandh.mes.dto.BatchDTO;
import com.anandh.mes.entity.Batch;
import com.anandh.mes.entity.ProductionOrder;
import com.anandh.mes.entity.Recipe;
import com.anandh.mes.enums.BatchStatus;
import com.anandh.mes.exception.ResourceNotFoundException;
import com.anandh.mes.repository.BatchRepository;
import com.anandh.mes.repository.ProductionOrderRepository;
import com.anandh.mes.repository.RecipeRepository;
import com.anandh.mes.service.BatchService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BatchServiceImpl implements BatchService {

    private final BatchRepository batchRepository;

    private final ProductionOrderRepository productionOrderRepository;

    private final RecipeRepository recipeRepository;

    // ==========================================================
    // CREATE
    // ==========================================================

    @Override
    public BatchDTO createBatch(BatchDTO dto) {

        ProductionOrder productionOrder =
                productionOrderRepository.findById(dto.getProductionOrderId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Production Order not found"));

        Recipe recipe =
                recipeRepository.findById(dto.getRecipeId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Recipe not found"));

        Batch batch = mapToEntity(dto);

        batch.setProductionOrder(productionOrder);
        batch.setRecipe(recipe);
        batch.setStatus(BatchStatus.CREATED);

        Batch saved = batchRepository.save(batch);

        return mapToDTO(saved);
    }

    // ==========================================================
    // READ
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<BatchDTO> getAllBatches() {

        return batchRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BatchDTO getBatchById(Long id) {

        Batch batch = batchRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Batch not found"));

        return mapToDTO(batch);
    }

    // ==========================================================
    // UPDATE
    // ==========================================================

    @Override
    public BatchDTO updateBatch(
            Long id,
            BatchDTO dto) {

        Batch batch = batchRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Batch not found"));

        ProductionOrder productionOrder =
                productionOrderRepository.findById(dto.getProductionOrderId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Production Order not found"));

        Recipe recipe =
                recipeRepository.findById(dto.getRecipeId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Recipe not found"));

        batch.setBatchNumber(dto.getBatchNumber());
        batch.setProductionOrder(productionOrder);
        batch.setRecipe(recipe);
        batch.setPlannedQuantity(dto.getPlannedQuantity());
        batch.setActualQuantity(dto.getActualQuantity());
        batch.setRemarks(dto.getRemarks());

        Batch updated = batchRepository.save(batch);

        return mapToDTO(updated);
    }

    // ==========================================================
    // DELETE
    // ==========================================================

    @Override
    public void deleteBatch(Long id) {

        Batch batch = batchRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Batch not found"));

        batchRepository.delete(batch);
    }

    // ==========================================================
    // SEARCH
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public BatchDTO getByBatchNumber(String batchNumber) {

        return mapToDTO(
                batchRepository.findByBatchNumber(batchNumber)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Batch not found")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BatchDTO> getByStatus(BatchStatus status) {

        return batchRepository.findByStatus(status)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BatchDTO> getByProductionOrder(Long productionOrderId) {

        return batchRepository
                .findByProductionOrderProductionOrderId(productionOrderId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BatchDTO> getByRecipe(Long recipeId) {

        return batchRepository
                .findByRecipeRecipeId(recipeId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // PAGINATION
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public Page<BatchDTO> getBatchPage(
            int page,
            int size,
            String sortBy) {

        Pageable pageable =
                PageRequest.of(page, size, Sort.by(sortBy));

        return batchRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    // ==========================================================
    // BATCH WORKFLOW
    // ==========================================================
    @Override
    public BatchDTO markReady(Long batchId) {

        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Batch not found"));

        batch.setStatus(BatchStatus.READY);

        return mapToDTO(batchRepository.save(batch));
    }

    @Override
    public BatchDTO startBatch(Long batchId) {

        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Batch not found"));

        batch.setStatus(BatchStatus.IN_PROGRESS);
        batch.setStartTime(java.time.LocalDateTime.now());

        return mapToDTO(batchRepository.save(batch));
    }

    @Override
    public BatchDTO holdBatch(Long batchId) {

        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Batch not found"));

        batch.setStatus(BatchStatus.ON_HOLD);

        return mapToDTO(batchRepository.save(batch));
    }

    @Override
    public BatchDTO resumeBatch(Long batchId) {

        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Batch not found"));

        batch.setStatus(BatchStatus.IN_PROGRESS);

        return mapToDTO(batchRepository.save(batch));
    }

    @Override
    public BatchDTO completeBatch(
            Long batchId,
            Double actualQuantity) {

        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Batch not found"));

        batch.setStatus(BatchStatus.COMPLETED);
        batch.setActualQuantity(actualQuantity);
        batch.setEndTime(java.time.LocalDateTime.now());

        return mapToDTO(batchRepository.save(batch));
    }

    @Override
    public BatchDTO closeBatch(Long batchId) {

        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Batch not found"));

        batch.setStatus(BatchStatus.CLOSED);

        return mapToDTO(batchRepository.save(batch));
    }

    @Override
    public BatchDTO cancelBatch(Long batchId) {

        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Batch not found"));

        batch.setStatus(BatchStatus.CANCELLED);

        return mapToDTO(batchRepository.save(batch));
    }

    // ==========================================================
    // DTO MAPPING
    // ==========================================================

    private BatchDTO mapToDTO(Batch batch) {

        return BatchDTO.builder()
                .batchId(batch.getBatchId())
                .batchNumber(batch.getBatchNumber())
                .productionOrderId(
                        batch.getProductionOrder().getProductionOrderId())
                .recipeId(batch.getRecipe().getRecipeId())
                .plannedQuantity(batch.getPlannedQuantity())
                .actualQuantity(batch.getActualQuantity())
                .status(batch.getStatus())
                .startTime(batch.getStartTime())
                .endTime(batch.getEndTime())
                .remarks(batch.getRemarks())
                .build();
    }

    private Batch mapToEntity(BatchDTO dto) {

        return Batch.builder()
                .batchNumber(dto.getBatchNumber())
                .plannedQuantity(dto.getPlannedQuantity())
                .actualQuantity(
                        dto.getActualQuantity() == null
                                ? 0.0
                                : dto.getActualQuantity())
                .status(dto.getStatus())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .remarks(dto.getRemarks())
                .build();
    }

}