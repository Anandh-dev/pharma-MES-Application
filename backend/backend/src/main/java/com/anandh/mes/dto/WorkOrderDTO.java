package com.anandh.mes.dto;

import java.time.LocalDateTime;

import com.anandh.mes.enums.WorkOrderStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkOrderDTO {

    private Long workOrderId;

    @NotBlank
    private String workOrderNumber;

    @NotNull
    private Long productionScheduleId;

    private Long batchId;

    @NotBlank
    private String productName;

    @NotNull
    @Positive
    private Double plannedQuantity;

    @NotBlank
    private String unit;

    @Positive
    private Integer priority;

    private WorkOrderStatus status;

    private LocalDateTime plannedStart;

    private LocalDateTime plannedEnd;

    private LocalDateTime actualStart;

    private LocalDateTime actualEnd;

    private String remarks;

}