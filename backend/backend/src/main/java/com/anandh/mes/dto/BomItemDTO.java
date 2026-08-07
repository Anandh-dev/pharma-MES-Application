package com.anandh.mes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BomItemDTO {

    private Long bomItemId;

    @NotNull
    private Long recipeId;

    @NotNull
    private Long materialId;

    @NotNull
    @Positive
    private Double quantity;

    @NotBlank
    private String unit;

    @NotNull
    private Integer sequence;

    private Boolean optionalMaterial;

    private String remarks;

}