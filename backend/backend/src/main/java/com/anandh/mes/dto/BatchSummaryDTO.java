package com.anandh.mes.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchSummaryDTO {

    private Long totalBatches;

    private Long completedBatches;

    private Long inProgressBatches;

    private Long onHoldBatches;

    private Long failedBatches;

}