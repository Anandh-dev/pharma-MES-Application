package com.anandh.mes.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.anandh.mes.enums.ProductionStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "production_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductionOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productionOrderId;

    @Column(nullable = false, unique = true)
    private String orderNumber;

    @Column(nullable = false, unique = true)
    private String batchNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    @Column(nullable = false)
    private Double plannedQuantity;

    @Builder.Default
    private Double producedQuantity = 0.0;

    @Column(nullable = false)
    private String unit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductionStatus status;

    private LocalDate plannedStartDate;

    private LocalDate plannedEndDate;

    private LocalDateTime actualStartTime;

    private LocalDateTime actualEndTime;

    private String remarks;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (status == null) {
            status = ProductionStatus.CREATED;
        }

        if (producedQuantity == null) {
            producedQuantity = 0.0;
        }
    }

    @PreUpdate
    public void onUpdate() {

        updatedAt = LocalDateTime.now();

    }

}