package com.anandh.mes.entity;

import java.time.LocalDateTime;

import com.anandh.mes.enums.ProductionScheduleStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "production_schedules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductionSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productionScheduleId;

    @Column(nullable = false, unique = true)
    private String scheduleNumber;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Double plannedQuantity;

    @Column(nullable = false)
    private String unit;

    @Column(nullable = false)
    private LocalDateTime plannedStart;

    @Column(nullable = false)
    private LocalDateTime plannedEnd;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductionScheduleStatus status;

    @Column(nullable = false)
    private Integer priority;

    @Column(length = 1000)
    private String remarks;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (status == null) {
            status = ProductionScheduleStatus.PLANNED;
        }

        if (priority == null) {
            priority = 1;
        }
    }

    @PreUpdate
    public void onUpdate() {

        updatedAt = LocalDateTime.now();

    }

}