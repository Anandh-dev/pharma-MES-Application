package com.anandh.mes.dto;

import java.time.LocalDateTime;

import com.anandh.mes.enums.ProductionEventType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductionExecutionEventDTO {

    private Long productionExecutionEventId;

    @NotNull
    private Long workOrderId;

    private Long batchId;

    private Long assignmentId;

    @NotNull
    private ProductionEventType eventType;

    @NotBlank
    private String operatorName;

    private LocalDateTime eventTime;

    private String remarks;

}