package com.anandh.mes.dto;

import java.time.LocalDateTime;

import com.anandh.mes.enums.BatchStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchDTO {

    private Long batchId;

    @NotBlank
    private String batchNumber;

    @NotNull
    private Long productionOrderId;

    @NotNull
    private Long recipeId;

    @NotNull
    @Positive
    private Double plannedQuantity;

    private Double actualQuantity;

    private BatchStatus status;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String remarks;

}