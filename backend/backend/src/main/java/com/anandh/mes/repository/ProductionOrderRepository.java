package com.anandh.mes.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.anandh.mes.entity.ProductionOrder;
import com.anandh.mes.enums.ProductionStatus;

public interface ProductionOrderRepository
        extends JpaRepository<ProductionOrder, Long> {

    Optional<ProductionOrder> findByOrderNumber(String orderNumber);

    Optional<ProductionOrder> findByBatchNumber(String batchNumber);

    List<ProductionOrder> findByStatus(ProductionStatus status);

    List<ProductionOrder> findByMaterialMaterialId(Long materialId);

    List<ProductionOrder> findByPlannedStartDate(LocalDate plannedStartDate);

    List<ProductionOrder> findByPlannedStartDateBetween(
            LocalDate startDate,
            LocalDate endDate);

    Page<ProductionOrder> findAll(Pageable pageable);

}