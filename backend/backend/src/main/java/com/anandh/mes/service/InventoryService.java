package com.anandh.mes.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.anandh.mes.dto.InventoryDTO;

public interface InventoryService {

    // CRUD Operations

    InventoryDTO createInventory(InventoryDTO inventoryDTO);

    List<InventoryDTO> getAllInventory();

    InventoryDTO getInventoryById(Long id);

    InventoryDTO updateInventory(
            Long id,
            InventoryDTO inventoryDTO);

    void deleteInventory(Long id);

    // Search Operations

    List<InventoryDTO> getInventoryByMaterial(Long materialId);

    List<InventoryDTO> getInventoryByWarehouse(Long warehouseId);

    List<InventoryDTO> getInventoryByBatch(String batchNumber);

    // Pagination

    Page<InventoryDTO> getInventoryPage(
            int page,
            int size,
            String sortBy);

    // Inventory Operations

    InventoryDTO stockIn(
            Long inventoryId,
            Double quantity);

    InventoryDTO stockOut(
            Long inventoryId,
            Double quantity);

    List<InventoryDTO> getLowStockInventory();

    List<InventoryDTO> getExpiredInventory();

    List<InventoryDTO> getNearExpiryInventory(int days);

}