package com.anandh.mes.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.anandh.mes.dto.EquipmentMaintenanceDTO;
import com.anandh.mes.service.EquipmentMaintenanceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/maintenance")
@RequiredArgsConstructor
public class EquipmentMaintenanceController {

    private final EquipmentMaintenanceService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EquipmentMaintenanceDTO createMaintenance(
            @Valid @RequestBody EquipmentMaintenanceDTO dto) {

        return service.createMaintenance(dto);
    }

    @GetMapping("/{equipmentId}")
    public List<EquipmentMaintenanceDTO> getHistory(
            @PathVariable Long equipmentId) {

        return service.getMaintenanceHistory(equipmentId);
    }
}