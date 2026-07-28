package com.anandh.mes.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.anandh.mes.dto.EquipmentDTO;
import com.anandh.mes.service.EquipmentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/equipment")
@RequiredArgsConstructor
public class EquipmentController {

    private final EquipmentService equipmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EquipmentDTO createEquipment(@Valid @RequestBody EquipmentDTO equipmentDTO) {
        return equipmentService.createEquipment(equipmentDTO);
    }

    @GetMapping
    public List<EquipmentDTO> getAllEquipment() {
        return equipmentService.getAllEquipment();
    }

    @GetMapping("/{id}")
    public EquipmentDTO getEquipmentById(@PathVariable Long id) {
        return equipmentService.getEquipmentById(id);
    }

    @PutMapping("/{id}")
    public EquipmentDTO updateEquipment(
            @PathVariable Long id,
            @Valid @RequestBody EquipmentDTO equipmentDTO) {

        return equipmentService.updateEquipment(id, equipmentDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEquipment(@PathVariable Long id) {
        equipmentService.deleteEquipment(id);
    }

}