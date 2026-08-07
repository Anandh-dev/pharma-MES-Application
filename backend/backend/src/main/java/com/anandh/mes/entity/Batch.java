package com.anandh.mes.entity;

import java.time.LocalDateTime;

import com.anandh.mes.enums.BatchStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "batches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchId;

    @Column(nullable = false, unique = true)
    private String batchNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "production_order_id", nullable = false)
    private ProductionOrder productionOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @Column(nullable = false)
    private Double plannedQuantity;

    private Double actualQuantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BatchStatus status;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Column(length = 1000)
    private String remarks;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (status == null) {
            status = BatchStatus.CREATED;
        }

        if (actualQuantity == null) {
            actualQuantity = 0.0;
        }
    }

    @PreUpdate
    public void onUpdate() {

        updatedAt = LocalDateTime.now();

    }

}