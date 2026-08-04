package com.anandh.mes.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.anandh.mes.dto.WarehouseDTO;
import com.anandh.mes.enums.WarehouseStatus;
import com.anandh.mes.enums.WarehouseType;

public interface WarehouseService {

    WarehouseDTO createWarehouse(WarehouseDTO warehouseDTO);

    List<WarehouseDTO> getAllWarehouses();

    WarehouseDTO getWarehouseById(Long id);

    WarehouseDTO updateWarehouse(Long id, WarehouseDTO warehouseDTO);

    void deleteWarehouse(Long id);

    List<WarehouseDTO> searchByName(String name);

    List<WarehouseDTO> getByType(WarehouseType type);

    List<WarehouseDTO> getByStatus(WarehouseStatus status);

    Page<WarehouseDTO> getWarehousePage(
            int page,
            int size,
            String sortBy);

}