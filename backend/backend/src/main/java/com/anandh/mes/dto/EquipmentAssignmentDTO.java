package com.anandh.mes.dto;

import java.time.LocalDateTime;

import com.anandh.mes.enums.EquipmentAssignmentStatus;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipmentAssignmentDTO {

    private Long equipmentAssignmentId;

    @NotNull
    private Long batchId;

    @NotNull
    private Long equipmentId;

    private EquipmentAssignmentStatus status;

    private LocalDateTime assignmentTime;

    private LocalDateTime releaseTime;

    private String operatorName;

    private String remarks;

}