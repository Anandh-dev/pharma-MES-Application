package com.anandh.mes.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.anandh.mes.dto.BatchDTO;
import com.anandh.mes.enums.BatchStatus;

public interface BatchService {

    // ==========================================================
    // CRUD
    // ==========================================================

    BatchDTO createBatch(
            BatchDTO batchDTO);

    List<BatchDTO> getAllBatches();

    BatchDTO getBatchById(
            Long id);

    BatchDTO updateBatch(
            Long id,
            BatchDTO batchDTO);

    void deleteBatch(
            Long id);

    // ==========================================================
    // SEARCH
    // ==========================================================

    BatchDTO getByBatchNumber(
            String batchNumber);

    List<BatchDTO> getByStatus(
            BatchStatus status);

    List<BatchDTO> getByProductionOrder(
            Long productionOrderId);

    List<BatchDTO> getByRecipe(
            Long recipeId);

    Page<BatchDTO> getBatchPage(
            int page,
            int size,
            String sortBy);

    // ==========================================================
    // BATCH WORKFLOW
    // ==========================================================

    BatchDTO markReady(
            Long batchId);

    BatchDTO startBatch(
            Long batchId);

    BatchDTO holdBatch(
            Long batchId);

    BatchDTO resumeBatch(
            Long batchId);

    BatchDTO completeBatch(
            Long batchId,
            Double actualQuantity);

    BatchDTO closeBatch(
            Long batchId);

    BatchDTO cancelBatch(
            Long batchId);

}