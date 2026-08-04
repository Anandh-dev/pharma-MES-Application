package com.anandh.mes.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.anandh.mes.entity.InventoryTransaction;
import com.anandh.mes.enums.MovementType;

public interface InventoryTransactionRepository
        extends JpaRepository<InventoryTransaction, Long> {

    List<InventoryTransaction> findByInventoryInventoryId(Long inventoryId);

    List<InventoryTransaction> findByMovementType(MovementType movementType);

    List<InventoryTransaction> findByTransactionTimeBetween(
            LocalDateTime start,
            LocalDateTime end);

    Page<InventoryTransaction> findAll(Pageable pageable);

}