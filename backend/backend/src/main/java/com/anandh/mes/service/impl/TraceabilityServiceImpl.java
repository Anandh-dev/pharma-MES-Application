package com.anandh.mes.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anandh.mes.dto.TraceabilityNodeDTO;
import com.anandh.mes.entity.BatchGenealogy;
import com.anandh.mes.exception.ResourceNotFoundException;
import com.anandh.mes.repository.BatchGenealogyRepository;
import com.anandh.mes.repository.BatchRepository;
import com.anandh.mes.service.TraceabilityService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TraceabilityServiceImpl
        implements TraceabilityService {

    private final BatchGenealogyRepository batchGenealogyRepository;

    private final BatchRepository batchRepository;

    // ==========================================================
    // BACKWARD TRACEABILITY
    // ==========================================================

    @Override
    public List<TraceabilityNodeDTO> getBackwardTraceability(
            Long batchId) {

        batchRepository.findById(batchId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Batch not found"));

        List<BatchGenealogy> genealogyList =
                batchGenealogyRepository
                        .findByChildBatchBatchId(batchId);

        List<TraceabilityNodeDTO> result =
                new ArrayList<>();

        for (BatchGenealogy genealogy : genealogyList) {

            result.add(
                    TraceabilityNodeDTO.builder()
                            .batchId(
                                    genealogy.getParentBatch()
                                            .getBatchId())
                            .batchNumber(
                                    genealogy.getParentBatch()
                                            .getBatchNumber())
                            .relationshipType(
                                    genealogy.getRelationshipType())
                            .quantity(
                                    genealogy.getQuantity())
                            .unit(
                                    genealogy.getUnit())
                            .build());
        }

        return result;
    }

    // ==========================================================
    // FORWARD TRACEABILITY
    // ==========================================================

    @Override
    public List<TraceabilityNodeDTO> getForwardTraceability(
            Long batchId) {

        batchRepository.findById(batchId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Batch not found"));

        List<BatchGenealogy> genealogyList =
                batchGenealogyRepository
                        .findByParentBatchBatchId(batchId);

        List<TraceabilityNodeDTO> result =
                new ArrayList<>();

        for (BatchGenealogy genealogy : genealogyList) {

            result.add(
                    TraceabilityNodeDTO.builder()
                            .batchId(
                                    genealogy.getChildBatch()
                                            .getBatchId())
                            .batchNumber(
                                    genealogy.getChildBatch()
                                            .getBatchNumber())
                            .relationshipType(
                                    genealogy.getRelationshipType())
                            .quantity(
                                    genealogy.getQuantity())
                            .unit(
                                    genealogy.getUnit())
                            .build());
        }

        return result;
    }

    // ==========================================================
    // IMPACT ANALYSIS
    // ==========================================================

    @Override
    public List<TraceabilityNodeDTO> getImpactAnalysis(
            Long batchId) {

        batchRepository.findById(batchId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Batch not found"));

        return getForwardTraceability(batchId);
    }

    // ==========================================================
    // RELATED BATCHES
    // ==========================================================

    @Override
    public List<TraceabilityNodeDTO> getRelatedBatches(
            Long batchId) {

        batchRepository.findById(batchId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Batch not found"));

        List<TraceabilityNodeDTO> result =
                new ArrayList<>();

        List<BatchGenealogy> parentRelationships =
                batchGenealogyRepository
                        .findByParentBatchBatchId(batchId);

        for (BatchGenealogy genealogy : parentRelationships) {

            result.add(
                    TraceabilityNodeDTO.builder()
                            .batchId(
                                    genealogy.getChildBatch()
                                            .getBatchId())
                            .batchNumber(
                                    genealogy.getChildBatch()
                                            .getBatchNumber())
                            .relationshipType(
                                    genealogy.getRelationshipType())
                            .quantity(
                                    genealogy.getQuantity())
                            .unit(
                                    genealogy.getUnit())
                            .build());
        }

        List<BatchGenealogy> childRelationships =
                batchGenealogyRepository
                        .findByChildBatchBatchId(batchId);

        for (BatchGenealogy genealogy : childRelationships) {

            result.add(
                    TraceabilityNodeDTO.builder()
                            .batchId(
                                    genealogy.getParentBatch()
                                            .getBatchId())
                            .batchNumber(
                                    genealogy.getParentBatch()
                                            .getBatchNumber())
                            .relationshipType(
                                    genealogy.getRelationshipType())
                            .quantity(
                                    genealogy.getQuantity())
                            .unit(
                                    genealogy.getUnit())
                            .build());
        }

        return result;
    }

}