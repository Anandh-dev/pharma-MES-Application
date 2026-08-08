package com.anandh.mes.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviationSummaryDTO {

    private Long totalDeviations;

    private Long openDeviations;

    private Long inProgressDeviations;

    private Long closedDeviations;

    private Long criticalDeviations;

    private Long highDeviations;

}