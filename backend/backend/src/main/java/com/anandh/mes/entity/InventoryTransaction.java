package com.anandh.mes.entity;

import java.time.LocalDateTime;

import com.anandh.mes.enums.MovementType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inventory_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventory;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MovementType movementType;

    @Column(nullable = false)
    private Double quantity;

    @Column(nullable = false)
    private LocalDateTime transactionTime;

    private String remarks;

    @PrePersist
    public void onCreate() {

        transactionTime = LocalDateTime.now();

    }

}