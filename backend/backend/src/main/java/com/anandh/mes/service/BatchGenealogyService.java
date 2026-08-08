package com.anandh.mes.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.anandh.mes.dto.BatchGenealogyDTO;
import com.anandh.mes.enums.GenealogyType;

public interface BatchGenealogyService {

    // ==========================================================
    // CRUD
    // ==========================================================

    BatchGenealogyDTO createBatchGenealogy(
            BatchGenealogyDTO dto);

    List<BatchGenealogyDTO> getAllBatchGenealogies();

    BatchGenealogyDTO getBatchGenealogyById(
            Long id);

    BatchGenealogyDTO updateBatchGenealogy(
            Long id,
            BatchGenealogyDTO dto);

    void deleteBatchGenealogy(
            Long id);

    // ==========================================================
    // TRACEABILITY SEARCH
    // ==========================================================

    List<BatchGenealogyDTO> getByParentBatch(
            Long parentBatchId);

    List<BatchGenealogyDTO> getByChildBatch(
            Long childBatchId);

    List<BatchGenealogyDTO> getByRelationshipType(
            GenealogyType relationshipType);

    List<BatchGenealogyDTO> getByParentBatchAndType(
            Long parentBatchId,
            GenealogyType relationshipType);

    List<BatchGenealogyDTO> getByChildBatchAndType(
            Long childBatchId,
            GenealogyType relationshipType);

    List<BatchGenealogyDTO> getByParentAndChild(
            Long parentBatchId,
            Long childBatchId);

    // ==========================================================
    // PAGINATION
    // ==========================================================

    Page<BatchGenealogyDTO> getBatchGenealogyPage(
            int page,
            int size,
            String sortBy);

    // ==========================================================
    // TRACEABILITY OPERATIONS
    // ==========================================================

    List<BatchGenealogyDTO> getForwardGenealogy(
            Long parentBatchId);

    List<BatchGenealogyDTO> getBackwardGenealogy(
            Long childBatchId);
}