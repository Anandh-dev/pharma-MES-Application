package com.anandh.mes.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "process_parameter_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessParameterLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long processParameterLogId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id", nullable = false)
    private Batch batch;

    @Column(nullable = false)
    private String parameterName;

    @Column(nullable = false)
    private Double parameterValue;

    @Column(nullable = false)
    private String unit;

    @Column(nullable = false)
    private String recordedBy;

    @Column(nullable = false)
    private LocalDateTime recordedTime;

    @Column(length = 1000)
    private String remarks;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (recordedTime == null) {
            recordedTime = LocalDateTime.now();
        }

    }

    @PreUpdate
    public void onUpdate() {

        updatedAt = LocalDateTime.now();

    }

}