package com.anandh.mes.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QualitySummaryDTO {

    private Long totalInspections;

    private Long passedInspections;

    private Long failedInspections;

    private Long pendingInspections;

}