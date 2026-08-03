package com.anandh.mes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.anandh.mes.entity.Equipment;
import com.anandh.mes.enums.EquipmentStatus;
import com.anandh.mes.enums.EquipmentType;

public interface EquipmentRepository
        extends JpaRepository<Equipment, Long> {

    Optional<Equipment> findByEquipmentCode(String equipmentCode);

    boolean existsByEquipmentCode(String equipmentCode);

    List<Equipment> findByEquipmentNameContainingIgnoreCase(String equipmentName);

    List<Equipment> findByStatus(EquipmentStatus status);

    List<Equipment> findByEquipmentType(EquipmentType equipmentType);

    List<Equipment> findByLocationContainingIgnoreCase(String location);

    Page<Equipment> findAll(Pageable pageable);

}