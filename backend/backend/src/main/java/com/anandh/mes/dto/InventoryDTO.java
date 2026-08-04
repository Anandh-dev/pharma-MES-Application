package com.anandh.mes.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryDTO {

    private Long inventoryId;

    @NotNull
    private Long materialId;

    @NotNull
    private Long warehouseId;

    @NotBlank
    private String batchNumber;

    @NotBlank
    private String lotNumber;

    @NotNull
    private Double currentQuantity;

    @NotNull
    private Double minimumStock;

    @NotNull
    private Double maximumStock;

    @NotBlank
    private String unit;

    private String supplier;

    private LocalDate manufacturingDate;

    private LocalDate expiryDate;
}