package com.anandh.mes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.anandh.mes.entity.EquipmentAssignment;
import com.anandh.mes.enums.EquipmentAssignmentStatus;

public interface EquipmentAssignmentRepository
        extends JpaRepository<EquipmentAssignment, Long> {

    List<EquipmentAssignment> findByBatchBatchId(
            Long batchId);

    List<EquipmentAssignment> findByEquipmentEquipmentId(
            Long equipmentId);

    List<EquipmentAssignment> findByStatus(
            EquipmentAssignmentStatus status);

    List<EquipmentAssignment> findByOperatorNameContainingIgnoreCase(
            String operatorName);

    List<EquipmentAssignment> findByBatchBatchIdAndEquipmentEquipmentId(
            Long batchId,
            Long equipmentId);

    Page<EquipmentAssignment> findAll(
            Pageable pageable);

}