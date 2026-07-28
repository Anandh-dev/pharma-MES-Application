package com.anandh.mes.service;

import java.util.List;

import com.anandh.mes.dto.EquipmentDTO;

public interface EquipmentService {

    EquipmentDTO createEquipment(EquipmentDTO equipmentDTO);

    List<EquipmentDTO> getAllEquipment();

    EquipmentDTO getEquipmentById(Long id);

    EquipmentDTO updateEquipment(Long id, EquipmentDTO equipmentDTO);

    void deleteEquipment(Long id);

}