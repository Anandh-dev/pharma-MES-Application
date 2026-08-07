package com.anandh.mes.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "material_consumptions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaterialConsumption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long materialConsumptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id", nullable = false)
    private Batch batch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    @Column(nullable = false)
    private Double plannedQuantity;

    @Column(nullable = false)
    private Double actualQuantity;

    @Column(nullable = false)
    private String unit;

    @Column(nullable = false)
    private LocalDateTime consumptionTime;

    private String operatorName;

    @Column(length = 1000)
    private String remarks;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (consumptionTime == null) {
            consumptionTime = LocalDateTime.now();
        }
    }

    @PreUpdate
    public void onUpdate() {

        updatedAt = LocalDateTime.now();

    }

}