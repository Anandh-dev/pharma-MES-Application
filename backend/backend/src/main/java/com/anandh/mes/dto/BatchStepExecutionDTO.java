package com.anandh.mes.dto;

import java.time.LocalDateTime;

import com.anandh.mes.enums.BatchStepStatus;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchStepExecutionDTO {

    private Long batchStepExecutionId;

    @NotNull
    private Long batchId;

    @NotNull
    private Long recipeStepId;

    @NotNull
    private Integer stepNumber;

    private BatchStepStatus status;

    private String operatorName;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String remarks;

}