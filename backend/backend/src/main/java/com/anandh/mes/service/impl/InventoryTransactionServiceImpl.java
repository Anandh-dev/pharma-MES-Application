package com.anandh.mes.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anandh.mes.dto.InventoryTransactionDTO;
import com.anandh.mes.entity.Inventory;
import com.anandh.mes.entity.InventoryTransaction;
import com.anandh.mes.enums.MovementType;
import com.anandh.mes.exception.ResourceNotFoundException;
import com.anandh.mes.repository.InventoryRepository;
import com.anandh.mes.repository.InventoryTransactionRepository;
import com.anandh.mes.service.InventoryTransactionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryTransactionServiceImpl
        implements InventoryTransactionService {

    private final InventoryTransactionRepository transactionRepository;

    private final InventoryRepository inventoryRepository;

    @Override
    public InventoryTransactionDTO createTransaction(
            InventoryTransactionDTO dto) {

        Inventory inventory = inventoryRepository.findById(dto.getInventoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Inventory not found"));

        InventoryTransaction transaction = InventoryTransaction.builder()
                .inventory(inventory)
                .movementType(dto.getMovementType())
                .quantity(dto.getQuantity())
                .remarks(dto.getRemarks())
                .build();

        return mapToDTO(transactionRepository.save(transaction));
    }

    @Override
    public void logTransaction(
            Inventory inventory,
            MovementType movementType,
            Double quantity,
            String remarks) {

        InventoryTransaction transaction = InventoryTransaction.builder()
                .inventory(inventory)
                .movementType(movementType)
                .quantity(quantity)
                .remarks(remarks)
                .build();

        transactionRepository.save(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryTransactionDTO> getAllTransactions() {

        return transactionRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public InventoryTransactionDTO getTransactionById(Long id) {

        InventoryTransaction transaction = transactionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Transaction not found"));

        return mapToDTO(transaction);
    }

    @Override
    public void deleteTransaction(Long id) {

        InventoryTransaction transaction = transactionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Transaction not found"));

        transactionRepository.delete(transaction);
    }

    @Override
    public List<InventoryTransactionDTO> getTransactionsByInventory(
            Long inventoryId) {

        return transactionRepository
                .findByInventoryInventoryId(inventoryId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<InventoryTransactionDTO> getTransactionsByMovementType(
            MovementType movementType) {

        return transactionRepository
                .findByMovementType(movementType)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<InventoryTransactionDTO> getTransactionsBetweenDates(
            LocalDateTime start,
            LocalDateTime end) {

        return transactionRepository
                .findByTransactionTimeBetween(start, end)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public Page<InventoryTransactionDTO> getTransactionPage(
            int page,
            int size,
            String sortBy) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sortBy));

        return transactionRepository
                .findAll(pageable)
                .map(this::mapToDTO);
    }

    private InventoryTransactionDTO mapToDTO(
            InventoryTransaction transaction) {

        return InventoryTransactionDTO.builder()
                .transactionId(transaction.getTransactionId())
                .inventoryId(transaction.getInventory().getInventoryId())
                .movementType(transaction.getMovementType())
                .quantity(transaction.getQuantity())
                .transactionTime(transaction.getTransactionTime())
                .remarks(transaction.getRemarks())
                .build();
    }

}