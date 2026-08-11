package com.anandh.mes.dto;

import java.time.LocalDateTime;

import com.anandh.mes.enums.KpiType;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductionKPIDTO {

    private Long productionKpiId;

    private Long workOrderId;

    private Long batchId;

    @NotNull
    private KpiType kpiType;

    @NotNull
    private Double kpiValue;

    private String unit;

    private LocalDateTime calculationStart;

    private LocalDateTime calculationEnd;

    private String remarks;

}