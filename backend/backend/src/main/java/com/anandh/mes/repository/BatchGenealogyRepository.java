package com.anandh.mes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.anandh.mes.entity.BatchGenealogy;
import com.anandh.mes.enums.GenealogyType;

public interface BatchGenealogyRepository
        extends JpaRepository<BatchGenealogy, Long> {

    // ==========================================================
    // PARENT / CHILD SEARCH
    // ==========================================================

    List<BatchGenealogy> findByParentBatchBatchId(
            Long parentBatchId);

    List<BatchGenealogy> findByChildBatchBatchId(
            Long childBatchId);

    // ==========================================================
    // RELATIONSHIP TYPE
    // ==========================================================

    List<BatchGenealogy> findByRelationshipType(
            GenealogyType relationshipType);

    // ==========================================================
    // PARENT + RELATIONSHIP TYPE
    // ==========================================================

    List<BatchGenealogy> findByParentBatchBatchIdAndRelationshipType(
            Long parentBatchId,
            GenealogyType relationshipType);

    // ==========================================================
    // CHILD + RELATIONSHIP TYPE
    // ==========================================================

    List<BatchGenealogy> findByChildBatchBatchIdAndRelationshipType(
            Long childBatchId,
            GenealogyType relationshipType);

    // ==========================================================
    // EXACT PARENT + CHILD RELATIONSHIP
    // ==========================================================

    List<BatchGenealogy> findByParentBatchBatchIdAndChildBatchBatchId(
            Long parentBatchId,
            Long childBatchId);

    // ==========================================================
    // PAGINATION
    // ==========================================================

    Page<BatchGenealogy> findAll(
            Pageable pageable);
}