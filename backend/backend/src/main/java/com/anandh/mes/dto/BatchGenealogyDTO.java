package com.anandh.mes.dto;

import com.anandh.mes.enums.GenealogyType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchGenealogyDTO {

    private Long batchGenealogyId;

    @NotNull
    private Long parentBatchId;

    @NotNull
    private Long childBatchId;

    @NotNull
    private GenealogyType relationshipType;

    @NotNull
    @Positive
    private Double quantity;

    @NotBlank
    private String unit;

    private String remarks;

}