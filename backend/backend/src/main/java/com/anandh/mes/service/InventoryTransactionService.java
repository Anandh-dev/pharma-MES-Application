package com.anandh.mes.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;

import com.anandh.mes.dto.InventoryTransactionDTO;
import com.anandh.mes.entity.Inventory;
import com.anandh.mes.enums.MovementType;

public interface InventoryTransactionService {

    // CRUD

    InventoryTransactionDTO createTransaction(
            InventoryTransactionDTO dto);

    List<InventoryTransactionDTO> getAllTransactions();

    InventoryTransactionDTO getTransactionById(Long id);

    void deleteTransaction(Long id);

    // Search

    List<InventoryTransactionDTO> getTransactionsByInventory(
            Long inventoryId);

    List<InventoryTransactionDTO> getTransactionsByMovementType(
            MovementType movementType);

    List<InventoryTransactionDTO> getTransactionsBetweenDates(
            LocalDateTime start,
            LocalDateTime end);

    Page<InventoryTransactionDTO> getTransactionPage(
            int page,
            int size,
            String sortBy);

    // Automatic Audit Trail

    void logTransaction(
            Inventory inventory,
            MovementType movementType,
            Double quantity,
            String remarks);

}