package com.anandh.mes.dto;

import java.time.LocalDateTime;

import com.anandh.mes.enums.DeviationSeverity;
import com.anandh.mes.enums.DeviationStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviationDTO {

    private Long deviationId;

    @NotBlank
    private String deviationNumber;

    @NotNull
    private Long batchId;

    private DeviationSeverity severity;

    @NotBlank
    private String description;

    private String rootCause;

    private String correctiveAction;

    private String preventiveAction;

    private DeviationStatus status;

    @NotBlank
    private String reportedBy;

    private LocalDateTime reportedDate;

}