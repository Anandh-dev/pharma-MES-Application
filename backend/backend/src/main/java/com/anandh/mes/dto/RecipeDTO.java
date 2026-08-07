package com.anandh.mes.dto;

import java.time.LocalDateTime;

import com.anandh.mes.enums.RecipeStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeDTO {

    private Long recipeId;

    @NotBlank
    private String recipeCode;

    @NotBlank
    private String recipeName;

    private Integer version;

    @NotNull
    private Long materialId;

    private RecipeStatus status;

    private String description;

    private LocalDateTime approvedAt;

    private String approvedBy;

}