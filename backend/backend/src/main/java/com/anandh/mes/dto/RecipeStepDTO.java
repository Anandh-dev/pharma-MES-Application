package com.anandh.mes.dto;

import com.anandh.mes.enums.RecipeStepType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeStepDTO {

    private Long recipeStepId;

    @NotNull
    private Long recipeId;

    @NotNull
    private Integer stepNumber;

    @NotBlank
    private String stepName;

    @NotNull
    private RecipeStepType stepType;

    private String description;

    @NotNull
    private Integer estimatedDuration;

    private String equipmentName;

    private Boolean criticalStep;

}