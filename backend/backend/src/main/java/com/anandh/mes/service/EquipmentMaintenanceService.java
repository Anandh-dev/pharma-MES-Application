package com.anandh.mes.service;

import java.util.List;

import com.anandh.mes.dto.EquipmentMaintenanceDTO;

public interface EquipmentMaintenanceService {

    EquipmentMaintenanceDTO createMaintenance(
            EquipmentMaintenanceDTO dto);

    List<EquipmentMaintenanceDTO> getMaintenanceHistory(
            Long equipmentId);

}