package com.anandh.mes.dto;

import java.time.LocalDateTime;

import com.anandh.mes.enums.BatchEventType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchEventLogDTO {

    private Long batchEventLogId;

    @NotNull
    private Long batchId;

    @NotNull
    private BatchEventType eventType;

    @NotBlank
    private String eventDescription;

    @NotBlank
    private String operatorName;

    private LocalDateTime eventTime;

    private String remarks;

}