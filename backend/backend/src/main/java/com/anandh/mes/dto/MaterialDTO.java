package com.anandh.mes.dto;

import com.anandh.mes.enums.MaterialCategory;
import com.anandh.mes.enums.MaterialStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaterialDTO {

    private Long materialId;

    @NotBlank
    private String materialCode;

    @NotBlank
    private String materialName;

    @NotNull
    private MaterialCategory category;

    @NotNull
    private MaterialStatus status;

    @NotBlank
    private String unit;

    private String manufacturer;

    private String description;
}