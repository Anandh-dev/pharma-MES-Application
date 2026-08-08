package com.anandh.mes.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardSummaryDTO {

    private BatchSummaryDTO production;

    private QualitySummaryDTO quality;

    private DeviationSummaryDTO deviations;

}