package com.anandh.mes.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anandh.mes.dto.EquipmentMaintenanceDTO;
import com.anandh.mes.entity.Equipment;
import com.anandh.mes.entity.EquipmentMaintenance;
import com.anandh.mes.exception.ResourceNotFoundException;
import com.anandh.mes.repository.EquipmentMaintenanceRepository;
import com.anandh.mes.repository.EquipmentRepository;
import com.anandh.mes.service.EquipmentMaintenanceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EquipmentMaintenanceServiceImpl
        implements EquipmentMaintenanceService {

    private final EquipmentMaintenanceRepository maintenanceRepository;
    private final EquipmentRepository equipmentRepository;

    @Override
    public EquipmentMaintenanceDTO createMaintenance(
            EquipmentMaintenanceDTO dto) {

        Equipment equipment = equipmentRepository.findById(dto.getEquipmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Equipment not found with id : "
                                        + dto.getEquipmentId()));

        EquipmentMaintenance maintenance = EquipmentMaintenance.builder()
                .equipment(equipment)
                .maintenanceDate(dto.getMaintenanceDate())
                .maintenanceType(dto.getMaintenanceType())
                .technicianName(dto.getTechnicianName())
                .remarks(dto.getRemarks())
                .nextMaintenanceDate(dto.getNextMaintenanceDate())
                .build();

        EquipmentMaintenance saved =
                maintenanceRepository.save(maintenance);

        return mapToDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EquipmentMaintenanceDTO> getMaintenanceHistory(
            Long equipmentId) {

        return maintenanceRepository
                .findByEquipmentEquipmentId(equipmentId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    private EquipmentMaintenanceDTO mapToDTO(
            EquipmentMaintenance maintenance) {

        return EquipmentMaintenanceDTO.builder()
                .maintenanceId(maintenance.getMaintenanceId())
                .equipmentId(maintenance.getEquipment().getEquipmentId())
                .maintenanceDate(maintenance.getMaintenanceDate())
                .maintenanceType(maintenance.getMaintenanceType())
                .technicianName(maintenance.getTechnicianName())
                .remarks(maintenance.getRemarks())
                .nextMaintenanceDate(
                        maintenance.getNextMaintenanceDate())
                .build();
    }
}