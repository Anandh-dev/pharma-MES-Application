package com.anandh.mes.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.anandh.mes.dto.BatchGenealogyDTO;
import com.anandh.mes.enums.GenealogyType;
import com.anandh.mes.service.BatchGenealogyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/batch-genealogy")
@RequiredArgsConstructor
public class BatchGenealogyController {

    private final BatchGenealogyService batchGenealogyService;

    // ==========================================================
    // CRUD
    // ==========================================================

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BatchGenealogyDTO createBatchGenealogy(
            @Valid @RequestBody BatchGenealogyDTO dto) {

        return batchGenealogyService.createBatchGenealogy(dto);
    }

    @GetMapping
    public List<BatchGenealogyDTO> getAllBatchGenealogies() {

        return batchGenealogyService.getAllBatchGenealogies();
    }

    @GetMapping("/{id}")
    public BatchGenealogyDTO getBatchGenealogyById(
            @PathVariable Long id) {

        return batchGenealogyService.getBatchGenealogyById(id);
    }

    @PutMapping("/{id}")
    public BatchGenealogyDTO updateBatchGenealogy(
            @PathVariable Long id,
            @Valid @RequestBody BatchGenealogyDTO dto) {

        return batchGenealogyService.updateBatchGenealogy(
                id,
                dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBatchGenealogy(
            @PathVariable Long id) {

        batchGenealogyService.deleteBatchGenealogy(id);
    }

    // ==========================================================
    // SEARCH
    // ==========================================================

    @GetMapping("/parent/{parentBatchId}")
    public List<BatchGenealogyDTO> getByParentBatch(
            @PathVariable Long parentBatchId) {

        return batchGenealogyService.getByParentBatch(
                parentBatchId);
    }

    @GetMapping("/child/{childBatchId}")
    public List<BatchGenealogyDTO> getByChildBatch(
            @PathVariable Long childBatchId) {

        return batchGenealogyService.getByChildBatch(
                childBatchId);
    }

    @GetMapping("/type/{relationshipType}")
    public List<BatchGenealogyDTO> getByRelationshipType(
            @PathVariable GenealogyType relationshipType) {

        return batchGenealogyService.getByRelationshipType(
                relationshipType);
    }

    @GetMapping("/parent/{parentBatchId}/type/{relationshipType}")
    public List<BatchGenealogyDTO> getByParentBatchAndType(
            @PathVariable Long parentBatchId,
            @PathVariable GenealogyType relationshipType) {

        return batchGenealogyService.getByParentBatchAndType(
                parentBatchId,
                relationshipType);
    }

    @GetMapping("/child/{childBatchId}/type/{relationshipType}")
    public List<BatchGenealogyDTO> getByChildBatchAndType(
            @PathVariable Long childBatchId,
            @PathVariable GenealogyType relationshipType) {

        return batchGenealogyService.getByChildBatchAndType(
                childBatchId,
                relationshipType);
    }

    @GetMapping("/parent/{parentBatchId}/child/{childBatchId}")
    public List<BatchGenealogyDTO> getByParentAndChild(
            @PathVariable Long parentBatchId,
            @PathVariable Long childBatchId) {

        return batchGenealogyService.getByParentAndChild(
                parentBatchId,
                childBatchId);
    }

    // ==========================================================
    // PAGINATION
    // ==========================================================

    @GetMapping("/page")
    public Page<BatchGenealogyDTO> getBatchGenealogyPage(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "batchGenealogyId")
            String sortBy) {

        return batchGenealogyService.getBatchGenealogyPage(
                page,
                size,
                sortBy);
    }

    // ==========================================================
    // TRACEABILITY
    // ==========================================================

    @GetMapping("/forward/{parentBatchId}")
    public List<BatchGenealogyDTO> getForwardGenealogy(
            @PathVariable Long parentBatchId) {

        return batchGenealogyService.getForwardGenealogy(
                parentBatchId);
    }

    @GetMapping("/backward/{childBatchId}")
    public List<BatchGenealogyDTO> getBackwardGenealogy(
            @PathVariable Long childBatchId) {

        return batchGenealogyService.getBackwardGenealogy(
                childBatchId);
    }

}