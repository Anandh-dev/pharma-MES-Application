package com.anandh.mes.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.anandh.mes.dto.EquipmentDTO;
import com.anandh.mes.enums.EquipmentStatus;
import com.anandh.mes.enums.EquipmentType;

public interface EquipmentService {

    EquipmentDTO createEquipment(EquipmentDTO equipmentDTO);

    EquipmentDTO updateEquipment(Long id,
                                 EquipmentDTO equipmentDTO);

    EquipmentDTO getEquipmentById(Long id);

    List<EquipmentDTO> getAllEquipment();

    void deleteEquipment(Long id);

    List<EquipmentDTO> searchByName(String name);

    List<EquipmentDTO> getByStatus(EquipmentStatus status);

    List<EquipmentDTO> getByType(EquipmentType type);

    List<EquipmentDTO> getByLocation(String location);

    Page<EquipmentDTO> getEquipmentPage(
            int page,
            int size,
            String sortBy);

}