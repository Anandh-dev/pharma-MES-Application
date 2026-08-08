package com.anandh.mes.dto;

import com.anandh.mes.enums.GenealogyType;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TraceabilityNodeDTO {

    private Long batchId;

    private String batchNumber;

    private GenealogyType relationshipType;

    private Double quantity;

    private String unit;

}