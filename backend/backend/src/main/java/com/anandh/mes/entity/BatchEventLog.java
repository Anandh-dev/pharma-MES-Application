package com.anandh.mes.entity;

import java.time.LocalDateTime;

import com.anandh.mes.enums.BatchEventType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "batch_event_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchEventLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchEventLogId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id", nullable = false)
    private Batch batch;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BatchEventType eventType;

    @Column(nullable = false, length = 2000)
    private String eventDescription;

    @Column(nullable = false)
    private String operatorName;

    @Column(nullable = false)
    private LocalDateTime eventTime;

    @Column(length = 1000)
    private String remarks;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (eventTime == null) {
            eventTime = LocalDateTime.now();
        }

    }

    @PreUpdate
    public void onUpdate() {

        updatedAt = LocalDateTime.now();

    }

}