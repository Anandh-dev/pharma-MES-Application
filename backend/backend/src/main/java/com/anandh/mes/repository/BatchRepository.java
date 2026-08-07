package com.anandh.mes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.anandh.mes.entity.Batch;
import com.anandh.mes.enums.BatchStatus;

public interface BatchRepository extends JpaRepository<Batch, Long> {

    Optional<Batch> findByBatchNumber(
            String batchNumber);

    List<Batch> findByStatus(
            BatchStatus status);

    List<Batch> findByProductionOrderProductionOrderId(
            Long productionOrderId);

    List<Batch> findByRecipeRecipeId(
            Long recipeId);

    Page<Batch> findAll(
            Pageable pageable);

}