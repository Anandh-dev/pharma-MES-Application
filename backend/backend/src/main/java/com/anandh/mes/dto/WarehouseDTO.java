package com.anandh.mes.dto;

import com.anandh.mes.enums.WarehouseStatus;
import com.anandh.mes.enums.WarehouseType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseDTO {

    private Long warehouseId;

    @NotBlank
    private String warehouseCode;

    @NotBlank
    private String warehouseName;

    @NotNull
    private WarehouseType warehouseType;

    @NotNull
    private WarehouseStatus status;

    @NotBlank
    private String location;

    private String description;

}