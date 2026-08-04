package com.anandh.mes.dto;

import java.time.LocalDateTime;

import com.anandh.mes.enums.MovementType;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryTransactionDTO {

    private Long transactionId;

    @NotNull
    private Long inventoryId;

    @NotNull
    private MovementType movementType;

    @NotNull
    private Double quantity;

    private LocalDateTime transactionTime;

    private String remarks;

}