package com.anandh.mes.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anandh.mes.dto.InventoryDTO;
import com.anandh.mes.entity.Inventory;
import com.anandh.mes.entity.Material;
import com.anandh.mes.entity.Warehouse;
import com.anandh.mes.enums.MovementType;
import com.anandh.mes.exception.ResourceNotFoundException;
import com.anandh.mes.repository.InventoryRepository;
import com.anandh.mes.repository.MaterialRepository;
import com.anandh.mes.repository.WarehouseRepository;
import com.anandh.mes.service.InventoryService;
import com.anandh.mes.service.InventoryTransactionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    private final MaterialRepository materialRepository;

    private final WarehouseRepository warehouseRepository;

    private final InventoryTransactionService transactionService;

    @Override
    public InventoryDTO createInventory(InventoryDTO dto) {

        Material material = materialRepository.findById(dto.getMaterialId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Material not found"));

        Warehouse warehouse = warehouseRepository.findById(dto.getWarehouseId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Warehouse not found"));

        Inventory inventory = mapToEntity(dto);

        inventory.setMaterial(material);
        inventory.setWarehouse(warehouse);

        Inventory savedInventory = inventoryRepository.save(inventory);

        return mapToDTO(savedInventory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryDTO> getAllInventory() {

        return inventoryRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public InventoryDTO getInventoryById(Long id) {

        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Inventory not found"));

        return mapToDTO(inventory);
    }

    @Override
    public InventoryDTO updateInventory(Long id, InventoryDTO dto) {

        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Inventory not found"));

        Material material = materialRepository.findById(dto.getMaterialId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Material not found"));

        Warehouse warehouse = warehouseRepository.findById(dto.getWarehouseId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Warehouse not found"));

        inventory.setMaterial(material);
        inventory.setWarehouse(warehouse);
        inventory.setBatchNumber(dto.getBatchNumber());
        inventory.setLotNumber(dto.getLotNumber());
        inventory.setCurrentQuantity(dto.getCurrentQuantity());
        inventory.setMinimumStock(dto.getMinimumStock());
        inventory.setMaximumStock(dto.getMaximumStock());
        inventory.setUnit(dto.getUnit());
        inventory.setSupplier(dto.getSupplier());
        inventory.setManufacturingDate(dto.getManufacturingDate());
        inventory.setExpiryDate(dto.getExpiryDate());

        Inventory updatedInventory = inventoryRepository.save(inventory);

        return mapToDTO(updatedInventory);
    }

    @Override
    public void deleteInventory(Long id) {

        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Inventory not found"));

        inventoryRepository.delete(inventory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryDTO> getInventoryByMaterial(Long materialId) {

        return inventoryRepository.findByMaterialMaterialId(materialId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryDTO> getInventoryByWarehouse(Long warehouseId) {

        return inventoryRepository.findByWarehouseWarehouseId(warehouseId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryDTO> getInventoryByBatch(String batchNumber) {

        return inventoryRepository.findByBatchNumber(batchNumber)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InventoryDTO> getInventoryPage(
            int page,
            int size,
            String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        return inventoryRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    @Override
    public InventoryDTO stockIn(Long inventoryId, Double quantity) {

        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Inventory not found"));

        inventory.setCurrentQuantity(
                inventory.getCurrentQuantity() + quantity);

        Inventory updatedInventory = inventoryRepository.save(inventory);

        transactionService.logTransaction(
                updatedInventory,
                MovementType.STOCK_IN,
                quantity,
                "Stock Added");

        return mapToDTO(updatedInventory);
    }

    @Override
    public InventoryDTO stockOut(Long inventoryId, Double quantity) {

        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Inventory not found"));

        if (inventory.getCurrentQuantity() < quantity) {
            throw new RuntimeException("Insufficient Stock");
        }

        inventory.setCurrentQuantity(
                inventory.getCurrentQuantity() - quantity);

        Inventory updatedInventory = inventoryRepository.save(inventory);

        transactionService.logTransaction(
                updatedInventory,
                MovementType.STOCK_OUT,
                quantity,
                "Stock Issued");

        return mapToDTO(updatedInventory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryDTO> getLowStockInventory() {

        return inventoryRepository.findAll()
                .stream()
                .filter(i -> i.getCurrentQuantity() <= i.getMinimumStock())
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryDTO> getExpiredInventory() {

        return inventoryRepository.findByExpiryDateBefore(LocalDate.now())
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryDTO> getNearExpiryInventory(int days) {

        return inventoryRepository.findByExpiryDateBetween(
                        LocalDate.now(),
                        LocalDate.now().plusDays(days))
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    private InventoryDTO mapToDTO(Inventory inventory) {

        return InventoryDTO.builder()
                .inventoryId(inventory.getInventoryId())
                .materialId(inventory.getMaterial().getMaterialId())
                .warehouseId(inventory.getWarehouse().getWarehouseId())
                .batchNumber(inventory.getBatchNumber())
                .lotNumber(inventory.getLotNumber())
                .currentQuantity(inventory.getCurrentQuantity())
                .minimumStock(inventory.getMinimumStock())
                .maximumStock(inventory.getMaximumStock())
                .unit(inventory.getUnit())
                .supplier(inventory.getSupplier())
                .manufacturingDate(inventory.getManufacturingDate())
                .expiryDate(inventory.getExpiryDate())
                .build();
    }

    private Inventory mapToEntity(InventoryDTO dto) {

        return Inventory.builder()
                .batchNumber(dto.getBatchNumber())
                .lotNumber(dto.getLotNumber())
                .currentQuantity(dto.getCurrentQuantity())
                .minimumStock(dto.getMinimumStock())
                .maximumStock(dto.getMaximumStock())
                .unit(dto.getUnit())
                .supplier(dto.getSupplier())
                .manufacturingDate(dto.getManufacturingDate())
                .expiryDate(dto.getExpiryDate())
                .build();
    }
}