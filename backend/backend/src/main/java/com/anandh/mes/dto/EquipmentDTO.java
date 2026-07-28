package com.anandh.mes.dto;

import com.anandh.mes.enums.EquipmentStatus;
import com.anandh.mes.enums.EquipmentType;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipmentDTO {

    private Long equipmentId;

    @NotBlank
    private String equipmentCode;

    @NotBlank
    private String equipmentName;

    private EquipmentType equipmentType;

    private EquipmentStatus status;

    private String location;

    private String manufacturer;

    private String model;
}