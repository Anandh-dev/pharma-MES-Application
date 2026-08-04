package com.anandh.mes.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.anandh.mes.dto.InventoryDTO;
import com.anandh.mes.service.InventoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    // =========================
    // CRUD Operations
    // =========================

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryDTO createInventory(
            @Valid @RequestBody InventoryDTO inventoryDTO) {

        return inventoryService.createInventory(inventoryDTO);
    }

    @GetMapping
    public List<InventoryDTO> getAllInventory() {

        return inventoryService.getAllInventory();
    }

    @GetMapping("/{id}")
    public InventoryDTO getInventoryById(
            @PathVariable Long id) {

        return inventoryService.getInventoryById(id);
    }

    @PutMapping("/{id}")
    public InventoryDTO updateInventory(
            @PathVariable Long id,
            @Valid @RequestBody InventoryDTO inventoryDTO) {

        return inventoryService.updateInventory(id, inventoryDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInventory(
            @PathVariable Long id) {

        inventoryService.deleteInventory(id);
    }

    // =========================
    // Search Operations
    // =========================

    @GetMapping("/material/{materialId}")
    public List<InventoryDTO> getInventoryByMaterial(
            @PathVariable Long materialId) {

        return inventoryService.getInventoryByMaterial(materialId);
    }

    @GetMapping("/warehouse/{warehouseId}")
    public List<InventoryDTO> getInventoryByWarehouse(
            @PathVariable Long warehouseId) {

        return inventoryService.getInventoryByWarehouse(warehouseId);
    }

    @GetMapping("/batch/{batchNumber}")
    public List<InventoryDTO> getInventoryByBatch(
            @PathVariable String batchNumber) {

        return inventoryService.getInventoryByBatch(batchNumber);
    }

    // =========================
    // Pagination
    // =========================

    @GetMapping("/page")
    public Page<InventoryDTO> getInventoryPage(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size,

            @RequestParam(defaultValue = "inventoryId")
            String sortBy) {

        return inventoryService.getInventoryPage(page, size, sortBy);
    }

    // =========================
    // Inventory Operations
    // =========================

    @PutMapping("/{id}/stock-in")
    public InventoryDTO stockIn(

            @PathVariable Long id,

            @RequestParam Double quantity) {

        return inventoryService.stockIn(id, quantity);
    }

    @PutMapping("/{id}/stock-out")
    public InventoryDTO stockOut(

            @PathVariable Long id,

            @RequestParam Double quantity) {

        return inventoryService.stockOut(id, quantity);
    }

    @GetMapping("/low-stock")
    public List<InventoryDTO> getLowStockInventory() {

        return inventoryService.getLowStockInventory();
    }

    @GetMapping("/expired")
    public List<InventoryDTO> getExpiredInventory() {

        return inventoryService.getExpiredInventory();
    }

    @GetMapping("/near-expiry")
    public List<InventoryDTO> getNearExpiryInventory(

            @RequestParam(defaultValue = "30")
            int days) {

        return inventoryService.getNearExpiryInventory(days);
    }

}