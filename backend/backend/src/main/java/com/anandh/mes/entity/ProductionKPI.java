package com.anandh.mes.entity;

import java.time.LocalDateTime;

import com.anandh.mes.enums.KpiType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "production_kpis")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductionKPI {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productionKpiId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_order_id")
    private WorkOrder workOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id")
    private Batch batch;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private KpiType kpiType;

    @Column(nullable = false)
    private Double kpiValue;

    private String unit;

    private LocalDateTime calculationStart;

    private LocalDateTime calculationEnd;

    @Column(length = 1000)
    private String remarks;

    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {

        createdAt = LocalDateTime.now();

    }

}