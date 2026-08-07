package com.anandh.mes.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.anandh.mes.enums.ProductionStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductionOrderDTO {

    private Long productionOrderId;

    @NotBlank
    private String orderNumber;

    @NotBlank
    private String batchNumber;

    @NotNull
    private Long materialId;

    @NotNull
    @Positive
    private Double plannedQuantity;

    private Double producedQuantity;

    @NotBlank
    private String unit;

    private ProductionStatus status;

    private LocalDate plannedStartDate;

    private LocalDate plannedEndDate;

    private LocalDateTime actualStartTime;

    private LocalDateTime actualEndTime;

    private String remarks;

}