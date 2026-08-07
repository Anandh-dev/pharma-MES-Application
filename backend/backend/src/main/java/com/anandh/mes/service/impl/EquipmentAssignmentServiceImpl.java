package com.anandh.mes.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anandh.mes.dto.EquipmentAssignmentDTO;
import com.anandh.mes.entity.Batch;
import com.anandh.mes.entity.Equipment;
import com.anandh.mes.entity.EquipmentAssignment;
import com.anandh.mes.enums.EquipmentAssignmentStatus;
import com.anandh.mes.exception.ResourceNotFoundException;
import com.anandh.mes.repository.BatchRepository;
import com.anandh.mes.repository.EquipmentAssignmentRepository;
import com.anandh.mes.repository.EquipmentRepository;
import com.anandh.mes.service.EquipmentAssignmentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EquipmentAssignmentServiceImpl
        implements EquipmentAssignmentService {

    private final EquipmentAssignmentRepository equipmentAssignmentRepository;

    private final BatchRepository batchRepository;

    private final EquipmentRepository equipmentRepository;

    // ==========================================================
    // CREATE
    // ==========================================================

    @Override
    public EquipmentAssignmentDTO createEquipmentAssignment(
            EquipmentAssignmentDTO dto) {

        Batch batch = batchRepository.findById(dto.getBatchId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Batch not found"));

        Equipment equipment = equipmentRepository.findById(dto.getEquipmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Equipment not found"));

        EquipmentAssignment assignment = mapToEntity(dto);

        assignment.setBatch(batch);
        assignment.setEquipment(equipment);

        EquipmentAssignment saved =
                equipmentAssignmentRepository.save(assignment);

        return mapToDTO(saved);
    }

    // ==========================================================
    // READ
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<EquipmentAssignmentDTO> getAllEquipmentAssignments() {

        return equipmentAssignmentRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EquipmentAssignmentDTO getEquipmentAssignmentById(
            Long id) {

        EquipmentAssignment assignment =
                equipmentAssignmentRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Equipment Assignment not found"));

        return mapToDTO(assignment);
    }

    // ==========================================================
    // UPDATE
    // ==========================================================

    @Override
    public EquipmentAssignmentDTO updateEquipmentAssignment(
            Long id,
            EquipmentAssignmentDTO dto) {

        EquipmentAssignment assignment =
                equipmentAssignmentRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Equipment Assignment not found"));

        Batch batch = batchRepository.findById(dto.getBatchId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Batch not found"));

        Equipment equipment =
                equipmentRepository.findById(dto.getEquipmentId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Equipment not found"));

        assignment.setBatch(batch);
        assignment.setEquipment(equipment);
        assignment.setStatus(dto.getStatus());
        assignment.setAssignmentTime(dto.getAssignmentTime());
        assignment.setReleaseTime(dto.getReleaseTime());
        assignment.setOperatorName(dto.getOperatorName());
        assignment.setRemarks(dto.getRemarks());

        EquipmentAssignment updated =
                equipmentAssignmentRepository.save(assignment);

        return mapToDTO(updated);
    }

    // ==========================================================
    // DELETE
    // ==========================================================

    @Override
    public void deleteEquipmentAssignment(Long id) {

        EquipmentAssignment assignment =
                equipmentAssignmentRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Equipment Assignment not found"));

        equipmentAssignmentRepository.delete(assignment);
    }

    // ==========================================================
    // SEARCH
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<EquipmentAssignmentDTO> getByBatch(Long batchId) {

        return equipmentAssignmentRepository.findByBatchBatchId(batchId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EquipmentAssignmentDTO> getByEquipment(
            Long equipmentId) {

        return equipmentAssignmentRepository
                .findByEquipmentEquipmentId(equipmentId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EquipmentAssignmentDTO> getByStatus(
            EquipmentAssignmentStatus status) {

        return equipmentAssignmentRepository
                .findByStatus(status)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EquipmentAssignmentDTO> getByOperator(
            String operatorName) {

        return equipmentAssignmentRepository
                .findByOperatorNameContainingIgnoreCase(operatorName)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EquipmentAssignmentDTO> getByBatchAndEquipment(
            Long batchId,
            Long equipmentId) {

        return equipmentAssignmentRepository
                .findByBatchBatchIdAndEquipmentEquipmentId(
                        batchId,
                        equipmentId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // PAGINATION
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public Page<EquipmentAssignmentDTO> getEquipmentAssignmentPage(
            int page,
            int size,
            String sortBy) {

        Pageable pageable =
                PageRequest.of(page, size, Sort.by(sortBy));

        return equipmentAssignmentRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    // ==========================================================
    // EQUIPMENT WORKFLOW
    // ==========================================================
    @Override
    public EquipmentAssignmentDTO assignEquipment(
            EquipmentAssignmentDTO dto) {

        return createEquipmentAssignment(dto);
    }

    @Override
    public EquipmentAssignmentDTO startEquipmentUsage(
            Long assignmentId) {

        EquipmentAssignment assignment =
                equipmentAssignmentRepository.findById(assignmentId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Equipment Assignment not found"));

        assignment.setStatus(EquipmentAssignmentStatus.IN_USE);

        return mapToDTO(
                equipmentAssignmentRepository.save(assignment));
    }

    @Override
    public EquipmentAssignmentDTO releaseEquipment(
            Long assignmentId) {

        EquipmentAssignment assignment =
                equipmentAssignmentRepository.findById(assignmentId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Equipment Assignment not found"));

        assignment.setStatus(EquipmentAssignmentStatus.RELEASED);
        assignment.setReleaseTime(java.time.LocalDateTime.now());

        return mapToDTO(
                equipmentAssignmentRepository.save(assignment));
    }

    // ==========================================================
    // DTO MAPPING
    // ==========================================================

    private EquipmentAssignmentDTO mapToDTO(
            EquipmentAssignment assignment) {

        return EquipmentAssignmentDTO.builder()
                .equipmentAssignmentId(
                        assignment.getEquipmentAssignmentId())
                .batchId(
                        assignment.getBatch().getBatchId())
                .equipmentId(
                        assignment.getEquipment().getEquipmentId())
                .status(
                        assignment.getStatus())
                .assignmentTime(
                        assignment.getAssignmentTime())
                .releaseTime(
                        assignment.getReleaseTime())
                .operatorName(
                        assignment.getOperatorName())
                .remarks(
                        assignment.getRemarks())
                .build();
    }

    private EquipmentAssignment mapToEntity(
            EquipmentAssignmentDTO dto) {

        return EquipmentAssignment.builder()
                .status(
                        dto.getStatus() == null
                                ? EquipmentAssignmentStatus.ASSIGNED
                                : dto.getStatus())
                .assignmentTime(
                        dto.getAssignmentTime())
                .releaseTime(
                        dto.getReleaseTime())
                .operatorName(
                        dto.getOperatorName())
                .remarks(
                        dto.getRemarks())
                .build();
    }

}