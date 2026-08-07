package com.anandh.mes.dto;

import java.time.LocalDateTime;

import com.anandh.mes.enums.BatchReleaseStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchReleaseDTO {

    private Long batchReleaseId;

    @NotNull
    private Long batchId;

    @NotNull
    private Long qualityInspectionId;

    @NotBlank
    private String approvedBy;

    private LocalDateTime releaseDate;

    private BatchReleaseStatus status;

    private String remarks;

}