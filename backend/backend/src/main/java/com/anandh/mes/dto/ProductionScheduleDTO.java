package com.anandh.mes.dto;

import java.time.LocalDateTime;

import com.anandh.mes.enums.ProductionScheduleStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductionScheduleDTO {

    private Long productionScheduleId;

    @NotBlank
    private String scheduleNumber;

    @NotBlank
    private String productName;

    @NotNull
    @Positive
    private Double plannedQuantity;

    @NotBlank
    private String unit;

    @NotNull
    private LocalDateTime plannedStart;

    @NotNull
    private LocalDateTime plannedEnd;

    private ProductionScheduleStatus status;

    @Positive
    private Integer priority;

    private String remarks;

}