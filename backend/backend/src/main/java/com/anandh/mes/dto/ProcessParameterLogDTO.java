package com.anandh.mes.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessParameterLogDTO {

    private Long processParameterLogId;

    @NotNull
    private Long batchId;

    @NotBlank
    private String parameterName;

    @NotNull
    private Double parameterValue;

    @NotBlank
    private String unit;

    @NotBlank
    private String recordedBy;

    private LocalDateTime recordedTime;

    private String remarks;

}