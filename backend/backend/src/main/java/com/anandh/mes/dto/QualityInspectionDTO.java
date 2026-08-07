package com.anandh.mes.dto;

import java.time.LocalDateTime;

import com.anandh.mes.enums.InspectionStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QualityInspectionDTO {

    private Long qualityInspectionId;

    @NotNull
    private Long batchId;

    @NotBlank
    private String inspectorName;

    private LocalDateTime inspectionDate;

    private InspectionStatus status;

    private String remarks;

}