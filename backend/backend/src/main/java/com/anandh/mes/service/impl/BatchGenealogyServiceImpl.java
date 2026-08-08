package com.anandh.mes.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anandh.mes.dto.BatchGenealogyDTO;
import com.anandh.mes.entity.Batch;
import com.anandh.mes.entity.BatchGenealogy;
import com.anandh.mes.enums.GenealogyType;
import com.anandh.mes.exception.ResourceNotFoundException;
import com.anandh.mes.repository.BatchGenealogyRepository;
import com.anandh.mes.repository.BatchRepository;
import com.anandh.mes.service.BatchGenealogyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BatchGenealogyServiceImpl
        implements BatchGenealogyService {

    private final BatchGenealogyRepository batchGenealogyRepository;

    private final BatchRepository batchRepository;

    // ==========================================================
    // CREATE
    // ==========================================================

    @Override
    public BatchGenealogyDTO createBatchGenealogy(
            BatchGenealogyDTO dto) {

        Batch parentBatch =
                batchRepository.findById(dto.getParentBatchId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Parent batch not found"));

        Batch childBatch =
                batchRepository.findById(dto.getChildBatchId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Child batch not found"));

        BatchGenealogy genealogy =
                mapToEntity(dto);

        genealogy.setParentBatch(parentBatch);
        genealogy.setChildBatch(childBatch);

        BatchGenealogy saved =
                batchGenealogyRepository.save(genealogy);

        return mapToDTO(saved);
    }

    // ==========================================================
    // READ ALL
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<BatchGenealogyDTO> getAllBatchGenealogies() {

        return batchGenealogyRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // READ BY ID
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public BatchGenealogyDTO getBatchGenealogyById(
            Long id) {

        BatchGenealogy genealogy =
                batchGenealogyRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Batch genealogy not found"));

        return mapToDTO(genealogy);
    }

    // ==========================================================
    // UPDATE
    // ==========================================================

    @Override
    public BatchGenealogyDTO updateBatchGenealogy(
            Long id,
            BatchGenealogyDTO dto) {

        BatchGenealogy genealogy =
                batchGenealogyRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Batch genealogy not found"));

        Batch parentBatch =
                batchRepository.findById(dto.getParentBatchId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Parent batch not found"));

        Batch childBatch =
                batchRepository.findById(dto.getChildBatchId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Child batch not found"));

        genealogy.setParentBatch(parentBatch);
        genealogy.setChildBatch(childBatch);
        genealogy.setRelationshipType(
                dto.getRelationshipType());
        genealogy.setQuantity(
                dto.getQuantity());
        genealogy.setUnit(
                dto.getUnit());
        genealogy.setRemarks(
                dto.getRemarks());

        BatchGenealogy updated =
                batchGenealogyRepository.save(genealogy);

        return mapToDTO(updated);
    }

    // ==========================================================
    // DELETE
    // ==========================================================

    @Override
    public void deleteBatchGenealogy(
            Long id) {

        BatchGenealogy genealogy =
                batchGenealogyRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Batch genealogy not found"));

        batchGenealogyRepository.delete(genealogy);
    }

    // ==========================================================
    // SEARCH BY PARENT
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<BatchGenealogyDTO> getByParentBatch(
            Long parentBatchId) {

        return batchGenealogyRepository
                .findByParentBatchBatchId(parentBatchId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // SEARCH BY CHILD
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<BatchGenealogyDTO> getByChildBatch(
            Long childBatchId) {

        return batchGenealogyRepository
                .findByChildBatchBatchId(childBatchId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // SEARCH BY RELATIONSHIP TYPE
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<BatchGenealogyDTO> getByRelationshipType(
            GenealogyType relationshipType) {

        return batchGenealogyRepository
                .findByRelationshipType(relationshipType)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // PARENT + TYPE
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<BatchGenealogyDTO> getByParentBatchAndType(
            Long parentBatchId,
            GenealogyType relationshipType) {

        return batchGenealogyRepository
                .findByParentBatchBatchIdAndRelationshipType(
                        parentBatchId,
                        relationshipType)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // CHILD + TYPE
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<BatchGenealogyDTO> getByChildBatchAndType(
            Long childBatchId,
            GenealogyType relationshipType) {

        return batchGenealogyRepository
                .findByChildBatchBatchIdAndRelationshipType(
                        childBatchId,
                        relationshipType)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // PARENT + CHILD
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<BatchGenealogyDTO> getByParentAndChild(
            Long parentBatchId,
            Long childBatchId) {

        return batchGenealogyRepository
                .findByParentBatchBatchIdAndChildBatchBatchId(
                        parentBatchId,
                        childBatchId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // PAGINATION
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public Page<BatchGenealogyDTO> getBatchGenealogyPage(
            int page,
            int size,
            String sortBy) {

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by(sortBy));

        return batchGenealogyRepository
                .findAll(pageable)
                .map(this::mapToDTO);
    }

    // ==========================================================
    // TRACEABILITY
    // ==========================================================
    // ==========================================================
    // FORWARD GENEALOGY
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<BatchGenealogyDTO> getForwardGenealogy(
            Long parentBatchId) {

        return batchGenealogyRepository
                .findByParentBatchBatchId(parentBatchId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // BACKWARD GENEALOGY
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<BatchGenealogyDTO> getBackwardGenealogy(
            Long childBatchId) {

        return batchGenealogyRepository
                .findByChildBatchBatchId(childBatchId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // DTO MAPPING
    // ==========================================================

    private BatchGenealogyDTO mapToDTO(
            BatchGenealogy genealogy) {

        return BatchGenealogyDTO.builder()
                .batchGenealogyId(
                        genealogy.getBatchGenealogyId())
                .parentBatchId(
                        genealogy.getParentBatch()
                                .getBatchId())
                .childBatchId(
                        genealogy.getChildBatch()
                                .getBatchId())
                .relationshipType(
                        genealogy.getRelationshipType())
                .quantity(
                        genealogy.getQuantity())
                .unit(
                        genealogy.getUnit())
                .remarks(
                        genealogy.getRemarks())
                .build();
    }

    private BatchGenealogy mapToEntity(
            BatchGenealogyDTO dto) {

        return BatchGenealogy.builder()
                .relationshipType(
                        dto.getRelationshipType())
                .quantity(
                        dto.getQuantity())
                .unit(
                        dto.getUnit())
                .remarks(
                        dto.getRemarks())
                .build();
    }

}