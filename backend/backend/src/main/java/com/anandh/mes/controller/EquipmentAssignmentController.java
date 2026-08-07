package com.anandh.mes.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.anandh.mes.dto.EquipmentAssignmentDTO;
import com.anandh.mes.enums.EquipmentAssignmentStatus;
import com.anandh.mes.service.EquipmentAssignmentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/equipment-assignments")
@RequiredArgsConstructor
public class EquipmentAssignmentController {

    private final EquipmentAssignmentService equipmentAssignmentService;

    // ==========================================================
    // CRUD
    // ==========================================================

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EquipmentAssignmentDTO createEquipmentAssignment(
            @Valid @RequestBody EquipmentAssignmentDTO equipmentAssignmentDTO) {

        return equipmentAssignmentService.createEquipmentAssignment(
                equipmentAssignmentDTO);
    }

    @GetMapping
    public List<EquipmentAssignmentDTO> getAllEquipmentAssignments() {

        return equipmentAssignmentService.getAllEquipmentAssignments();
    }

    @GetMapping("/{id}")
    public EquipmentAssignmentDTO getEquipmentAssignmentById(
            @PathVariable Long id) {

        return equipmentAssignmentService.getEquipmentAssignmentById(id);
    }

    @PutMapping("/{id}")
    public EquipmentAssignmentDTO updateEquipmentAssignment(
            @PathVariable Long id,
            @Valid @RequestBody EquipmentAssignmentDTO equipmentAssignmentDTO) {

        return equipmentAssignmentService.updateEquipmentAssignment(
                id,
                equipmentAssignmentDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEquipmentAssignment(
            @PathVariable Long id) {

        equipmentAssignmentService.deleteEquipmentAssignment(id);
    }

    // ==========================================================
    // SEARCH
    // ==========================================================

    @GetMapping("/batch/{batchId}")
    public List<EquipmentAssignmentDTO> getByBatch(
            @PathVariable Long batchId) {

        return equipmentAssignmentService.getByBatch(batchId);
    }

    @GetMapping("/equipment/{equipmentId}")
    public List<EquipmentAssignmentDTO> getByEquipment(
            @PathVariable Long equipmentId) {

        return equipmentAssignmentService.getByEquipment(equipmentId);
    }

    @GetMapping("/status/{status}")
    public List<EquipmentAssignmentDTO> getByStatus(
            @PathVariable EquipmentAssignmentStatus status) {

        return equipmentAssignmentService.getByStatus(status);
    }

    @GetMapping("/operator")
    public List<EquipmentAssignmentDTO> getByOperator(
            @RequestParam String operatorName) {

        return equipmentAssignmentService.getByOperator(operatorName);
    }

    @GetMapping("/batch/{batchId}/equipment/{equipmentId}")
    public List<EquipmentAssignmentDTO> getByBatchAndEquipment(
            @PathVariable Long batchId,
            @PathVariable Long equipmentId) {

        return equipmentAssignmentService.getByBatchAndEquipment(
                batchId,
                equipmentId);
    }

    @GetMapping("/page")
    public Page<EquipmentAssignmentDTO> getEquipmentAssignmentPage(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "equipmentAssignmentId")
            String sortBy) {

        return equipmentAssignmentService.getEquipmentAssignmentPage(
                page,
                size,
                sortBy);
    }

    // ==========================================================
    // EQUIPMENT WORKFLOW
    // ==========================================================

    @PostMapping("/assign")
    public EquipmentAssignmentDTO assignEquipment(
            @Valid @RequestBody EquipmentAssignmentDTO equipmentAssignmentDTO) {

        return equipmentAssignmentService.assignEquipment(
                equipmentAssignmentDTO);
    }

    @PutMapping("/{id}/start")
    public EquipmentAssignmentDTO startEquipmentUsage(
            @PathVariable Long id) {

        return equipmentAssignmentService.startEquipmentUsage(id);
    }

    @PutMapping("/{id}/release")
    public EquipmentAssignmentDTO releaseEquipment(
            @PathVariable Long id) {

        return equipmentAssignmentService.releaseEquipment(id);
    }

}