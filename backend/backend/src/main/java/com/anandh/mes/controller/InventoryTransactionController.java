package com.anandh.mes.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.anandh.mes.dto.InventoryTransactionDTO;
import com.anandh.mes.enums.MovementType;
import com.anandh.mes.service.InventoryTransactionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inventory-transactions")
@RequiredArgsConstructor
public class InventoryTransactionController {

    private final InventoryTransactionService inventoryTransactionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryTransactionDTO createTransaction(
            @Valid @RequestBody InventoryTransactionDTO dto) {

        return inventoryTransactionService.createTransaction(dto);
    }

    @GetMapping
    public List<InventoryTransactionDTO> getAllTransactions() {

        return inventoryTransactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public InventoryTransactionDTO getTransactionById(
            @PathVariable Long id) {

        return inventoryTransactionService.getTransactionById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTransaction(
            @PathVariable Long id) {

        inventoryTransactionService.deleteTransaction(id);
    }

    @GetMapping("/inventory/{inventoryId}")
    public List<InventoryTransactionDTO> getByInventory(
            @PathVariable Long inventoryId) {

        return inventoryTransactionService
                .getTransactionsByInventory(inventoryId);
    }

    @GetMapping("/movement/{movementType}")
    public List<InventoryTransactionDTO> getByMovementType(
            @PathVariable MovementType movementType) {

        return inventoryTransactionService
                .getTransactionsByMovementType(movementType);
    }

    @GetMapping("/date-range")
    public List<InventoryTransactionDTO> getBetweenDates(

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime start,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime end) {

        return inventoryTransactionService
                .getTransactionsBetweenDates(start, end);
    }

    @GetMapping("/page")
    public Page<InventoryTransactionDTO> getPage(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "transactionId")
            String sortBy) {

        return inventoryTransactionService
                .getTransactionPage(page, size, sortBy);
    }

}