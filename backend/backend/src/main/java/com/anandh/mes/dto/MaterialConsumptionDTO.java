package com.anandh.mes.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaterialConsumptionDTO {

    private Long materialConsumptionId;

    @NotNull
    private Long batchId;

    @NotNull
    private Long materialId;

    @NotNull
    @Positive
    private Double plannedQuantity;

    @NotNull
    @Positive
    private Double actualQuantity;

    @NotBlank
    private String unit;

    private LocalDateTime consumptionTime;

    private String operatorName;

    private String remarks;

}