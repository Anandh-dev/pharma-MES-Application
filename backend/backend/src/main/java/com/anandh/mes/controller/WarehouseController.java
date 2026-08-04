package com.anandh.mes.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.anandh.mes.dto.WarehouseDTO;
import com.anandh.mes.enums.WarehouseStatus;
import com.anandh.mes.enums.WarehouseType;
import com.anandh.mes.service.WarehouseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/warehouses")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WarehouseDTO createWarehouse(
            @Valid @RequestBody WarehouseDTO warehouseDTO) {

        return warehouseService.createWarehouse(warehouseDTO);
    }

    @GetMapping
    public List<WarehouseDTO> getAllWarehouses() {

        return warehouseService.getAllWarehouses();
    }

    @GetMapping("/{id}")
    public WarehouseDTO getWarehouseById(
            @PathVariable Long id) {

        return warehouseService.getWarehouseById(id);
    }

    @PutMapping("/{id}")
    public WarehouseDTO updateWarehouse(
            @PathVariable Long id,
            @Valid @RequestBody WarehouseDTO warehouseDTO) {

        return warehouseService.updateWarehouse(id, warehouseDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWarehouse(
            @PathVariable Long id) {

        warehouseService.deleteWarehouse(id);
    }

    @GetMapping("/search")
    public List<WarehouseDTO> searchWarehouse(
            @RequestParam String name) {

        return warehouseService.searchByName(name);
    }

    @GetMapping("/type/{type}")
    public List<WarehouseDTO> getByType(
            @PathVariable WarehouseType type) {

        return warehouseService.getByType(type);
    }

    @GetMapping("/status/{status}")
    public List<WarehouseDTO> getByStatus(
            @PathVariable WarehouseStatus status) {

        return warehouseService.getByStatus(status);
    }

    @GetMapping("/page")
    public Page<WarehouseDTO> getWarehousePage(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size,

            @RequestParam(defaultValue = "warehouseId")
            String sortBy) {

        return warehouseService.getWarehousePage(
                page,
                size,
                sortBy);
    }
}