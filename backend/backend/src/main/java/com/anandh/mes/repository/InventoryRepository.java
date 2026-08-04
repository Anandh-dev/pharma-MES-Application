package com.anandh.mes.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.anandh.mes.entity.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    // Search by Batch Number
    List<Inventory> findByBatchNumber(String batchNumber);

    // Search by Lot Number
    List<Inventory> findByLotNumber(String lotNumber);

    // Search by Material
    List<Inventory> findByMaterialMaterialId(Long materialId);

    // Search by Warehouse
    List<Inventory> findByWarehouseWarehouseId(Long warehouseId);

    // Expired Inventory
    List<Inventory> findByExpiryDateBefore(LocalDate date);

    // Near Expiry Inventory
    List<Inventory> findByExpiryDateBetween(
            LocalDate startDate,
            LocalDate endDate);

    // Low Stock
    List<Inventory> findByCurrentQuantityLessThan(Double quantity);

    // Pagination
    Page<Inventory> findAll(Pageable pageable);

}