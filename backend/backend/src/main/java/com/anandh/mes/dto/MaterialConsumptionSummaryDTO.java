package com.anandh.mes.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaterialConsumptionSummaryDTO {

    private Long materialId;

    private String materialName;

    private Double plannedQuantity;

    private Double actualQuantity;

    private String unit;

}