package com.anandh.mes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QualityTestResultDTO {

    private Long qualityTestResultId;

    @NotNull
    private Long qualityInspectionId;

    @NotBlank
    private String testName;

    @NotBlank
    private String expectedValue;

    @NotBlank
    private String actualValue;

    @NotBlank
    private String unit;

    private Boolean passed;

    private String remarks;

}