package com.anandh.mes.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.anandh.mes.dto.EquipmentDTO;
import com.anandh.mes.enums.EquipmentStatus;
import com.anandh.mes.enums.EquipmentType;
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
    public EquipmentDTO createEquipment(
            @Valid @RequestBody EquipmentDTO equipmentDTO) {

        return equipmentService.createEquipment(equipmentDTO);
    }

    @GetMapping
    public List<EquipmentDTO> getAllEquipment() {

        return equipmentService.getAllEquipment();
    }

    @GetMapping("/{id}")
    public EquipmentDTO getEquipmentById(
            @PathVariable Long id) {

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
    public void deleteEquipment(
            @PathVariable Long id) {

        equipmentService.deleteEquipment(id);
    }

    // Search by Equipment Name
    @GetMapping("/search")
    public List<EquipmentDTO> searchEquipment(
            @RequestParam String name) {

        return equipmentService.searchByName(name);
    }

    // Filter by Status
    @GetMapping("/status/{status}")
    public List<EquipmentDTO> getByStatus(
            @PathVariable EquipmentStatus status) {

        return equipmentService.getByStatus(status);
    }

    // Filter by Equipment Type
    @GetMapping("/type/{type}")
    public List<EquipmentDTO> getByType(
            @PathVariable EquipmentType type) {

        return equipmentService.getByType(type);
    }

    // Filter by Location
    @GetMapping("/location")
    public List<EquipmentDTO> getByLocation(
            @RequestParam String location) {

        return equipmentService.getByLocation(location);
    }

    // Pagination & Sorting
    @GetMapping("/page")
    public Page<EquipmentDTO> getEquipmentPage(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size,

            @RequestParam(defaultValue = "equipmentId")
            String sortBy) {

        return equipmentService.getEquipmentPage(
                page,
                size,
                sortBy);
    }
}