package com.anandh.mes.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.anandh.mes.dto.EquipmentAssignmentDTO;
import com.anandh.mes.enums.EquipmentAssignmentStatus;

public interface EquipmentAssignmentService {

    // ==========================================================
    // CRUD
    // ==========================================================

    EquipmentAssignmentDTO createEquipmentAssignment(
            EquipmentAssignmentDTO equipmentAssignmentDTO);

    List<EquipmentAssignmentDTO> getAllEquipmentAssignments();

    EquipmentAssignmentDTO getEquipmentAssignmentById(
            Long id);

    EquipmentAssignmentDTO updateEquipmentAssignment(
            Long id,
            EquipmentAssignmentDTO equipmentAssignmentDTO);

    void deleteEquipmentAssignment(
            Long id);

    // ==========================================================
    // SEARCH
    // ==========================================================

    List<EquipmentAssignmentDTO> getByBatch(
            Long batchId);

    List<EquipmentAssignmentDTO> getByEquipment(
            Long equipmentId);

    List<EquipmentAssignmentDTO> getByStatus(
            EquipmentAssignmentStatus status);

    List<EquipmentAssignmentDTO> getByOperator(
            String operatorName);

    List<EquipmentAssignmentDTO> getByBatchAndEquipment(
            Long batchId,
            Long equipmentId);

    Page<EquipmentAssignmentDTO> getEquipmentAssignmentPage(
            int page,
            int size,
            String sortBy);

    // ==========================================================
    // EQUIPMENT WORKFLOW
    // ==========================================================

    EquipmentAssignmentDTO assignEquipment(
            EquipmentAssignmentDTO equipmentAssignmentDTO);

    EquipmentAssignmentDTO startEquipmentUsage(
            Long assignmentId);

    EquipmentAssignmentDTO releaseEquipment(
            Long assignmentId);

}