package com.anandh.mes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeParameterDTO {

    private Long recipeParameterId;

    @NotNull
    private Long recipeStepId;

    @NotBlank
    private String parameterName;

    @NotBlank
    private String parameterValue;

    @NotBlank
    private String unit;

    private Double minimumValue;

    private Double maximumValue;

    private Boolean mandatory;

    private String remarks;

}