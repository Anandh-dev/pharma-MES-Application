package com.anandh.mes.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipmentMaintenanceDTO {

    private Long maintenanceId;

    @NotNull
    private Long equipmentId;

    @NotNull
    private LocalDate maintenanceDate;

    private String maintenanceType;

    private String technicianName;

    private String remarks;

    private LocalDate nextMaintenanceDate;
}