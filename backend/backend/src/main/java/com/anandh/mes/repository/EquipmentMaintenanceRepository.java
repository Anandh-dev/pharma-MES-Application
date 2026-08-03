package com.anandh.mes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anandh.mes.entity.EquipmentMaintenance;

public interface EquipmentMaintenanceRepository
        extends JpaRepository<EquipmentMaintenance, Long> {

    List<EquipmentMaintenance> findByEquipmentEquipmentId(Long equipmentId);

}