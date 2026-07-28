package com.anandh.mes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anandh.mes.entity.Equipment;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

}