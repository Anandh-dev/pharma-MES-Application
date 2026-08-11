package com.anandh.mes.dto;

import java.time.LocalDateTime;

import com.anandh.mes.enums.AssignmentStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkOrderAssignmentDTO {

    private Long assignmentId;

    @NotNull
    private Long workOrderId;

    @NotBlank
    private String operatorName;

    @NotBlank
    private String workCenter;

    @NotBlank
    private String operationName;

    private LocalDateTime assignedAt;

    private LocalDateTime startedAt;

    private LocalDateTime completedAt;

    private AssignmentStatus status;

    private String remarks;

}