package com.anandh.mes.entity;

import java.time.LocalDateTime;

import com.anandh.mes.enums.ProductionEventType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "production_execution_events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductionExecutionEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productionExecutionEventId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_order_id", nullable = false)
    private WorkOrder workOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id")
    private Batch batch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id")
    private WorkOrderAssignment assignment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductionEventType eventType;

    @Column(nullable = false)
    private String operatorName;

    private LocalDateTime eventTime;

    @Column(length = 1000)
    private String remarks;

    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {

        createdAt = LocalDateTime.now();

        if (eventTime == null) {
            eventTime = LocalDateTime.now();
        }

    }

}