package com.anandh.mes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.anandh.mes.entity.Warehouse;
import com.anandh.mes.enums.WarehouseStatus;
import com.anandh.mes.enums.WarehouseType;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    Optional<Warehouse> findByWarehouseCode(String warehouseCode);

    boolean existsByWarehouseCode(String warehouseCode);

    List<Warehouse> findByWarehouseNameContainingIgnoreCase(String warehouseName);

    List<Warehouse> findByWarehouseType(WarehouseType warehouseType);

    List<Warehouse> findByStatus(WarehouseStatus status);

    Page<Warehouse> findAll(Pageable pageable);

}