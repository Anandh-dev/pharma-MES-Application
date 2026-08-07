package com.anandh.mes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.anandh.mes.entity.BatchStepExecution;
import com.anandh.mes.enums.BatchStepStatus;

public interface BatchStepExecutionRepository
        extends JpaRepository<BatchStepExecution, Long> {

    List<BatchStepExecution> findByBatchBatchIdOrderByStepNumberAsc(
            Long batchId);

    List<BatchStepExecution> findByRecipeStepRecipeStepId(
            Long recipeStepId);

    List<BatchStepExecution> findByStatus(
            BatchStepStatus status);

    List<BatchStepExecution> findByOperatorNameContainingIgnoreCase(
            String operatorName);

    Page<BatchStepExecution> findAll(
            Pageable pageable);

}