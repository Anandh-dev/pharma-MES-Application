package com.anandh.mes.entity;

import java.time.LocalDateTime;

import com.anandh.mes.enums.WorkOrderStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "work_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workOrderId;

    @Column(nullable = false, unique = true)
    private String workOrderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "production_schedule_id", nullable = false)
    private ProductionSchedule productionSchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id")
    private Batch batch;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Double plannedQuantity;

    @Column(nullable = false)
    private String unit;

    @Column(nullable = false)
    private Integer priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkOrderStatus status;

    private LocalDateTime plannedStart;

    private LocalDateTime plannedEnd;

    private LocalDateTime actualStart;

    private LocalDateTime actualEnd;

    @Column(length = 1000)
    private String remarks;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (status == null) {
            status = WorkOrderStatus.DRAFT;
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