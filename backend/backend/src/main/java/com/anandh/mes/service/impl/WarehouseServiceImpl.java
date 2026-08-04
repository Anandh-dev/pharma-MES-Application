package com.anandh.mes.service.impl;

import java.util.List;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anandh.mes.dto.WarehouseDTO;
import com.anandh.mes.entity.Warehouse;
import com.anandh.mes.enums.WarehouseStatus;
import com.anandh.mes.enums.WarehouseType;
import com.anandh.mes.exception.ResourceNotFoundException;
import com.anandh.mes.repository.WarehouseRepository;
import com.anandh.mes.service.WarehouseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;

    @Override
    public WarehouseDTO createWarehouse(WarehouseDTO dto) {

        if (warehouseRepository.existsByWarehouseCode(dto.getWarehouseCode())) {
            throw new RuntimeException("Warehouse Code already exists");
        }

        Warehouse warehouse = mapToEntity(dto);

        Warehouse savedWarehouse = warehouseRepository.save(warehouse);

        return mapToDTO(savedWarehouse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WarehouseDTO> getAllWarehouses() {

        return warehouseRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public WarehouseDTO getWarehouseById(Long id) {

        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Warehouse not found with id : " + id));

        return mapToDTO(warehouse);
    }

    @Override
    public WarehouseDTO updateWarehouse(Long id,
                                        WarehouseDTO dto) {

        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Warehouse not found with id : " + id));

        warehouse.setWarehouseCode(dto.getWarehouseCode());
        warehouse.setWarehouseName(dto.getWarehouseName());
        warehouse.setWarehouseType(dto.getWarehouseType());
        warehouse.setStatus(dto.getStatus());
        warehouse.setLocation(dto.getLocation());
        warehouse.setDescription(dto.getDescription());

        Warehouse updatedWarehouse = warehouseRepository.save(warehouse);

        return mapToDTO(updatedWarehouse);
    }

    @Override
    public void deleteWarehouse(Long id) {

        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Warehouse not found with id : " + id));

        warehouseRepository.delete(warehouse);
    }

    @Override
    public List<WarehouseDTO> searchByName(String name) {

        return warehouseRepository.findByWarehouseNameContainingIgnoreCase(name)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<WarehouseDTO> getByType(WarehouseType type) {

        return warehouseRepository.findByWarehouseType(type)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<WarehouseDTO> getByStatus(WarehouseStatus status) {

        return warehouseRepository.findByStatus(status)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public Page<WarehouseDTO> getWarehousePage(
            int page,
            int size,
            String sortBy) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sortBy));

        return warehouseRepository
                .findAll(pageable)
                .map(this::mapToDTO);
    }

    private WarehouseDTO mapToDTO(Warehouse warehouse) {

        return WarehouseDTO.builder()
                .warehouseId(warehouse.getWarehouseId())
                .warehouseCode(warehouse.getWarehouseCode())
                .warehouseName(warehouse.getWarehouseName())
                .warehouseType(warehouse.getWarehouseType())
                .status(warehouse.getStatus())
                .location(warehouse.getLocation())
                .description(warehouse.getDescription())
                .build();
    }

    private Warehouse mapToEntity(WarehouseDTO dto) {

        return Warehouse.builder()
                .warehouseCode(dto.getWarehouseCode())
                .warehouseName(dto.getWarehouseName())
                .warehouseType(dto.getWarehouseType())
                .status(dto.getStatus())
                .location(dto.getLocation())
                .description(dto.getDescription())
                .build();
    }
}