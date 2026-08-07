package com.anandh.mes.entity;

import java.time.LocalDateTime;

import com.anandh.mes.enums.BatchReleaseStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "batch_releases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchRelease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchReleaseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id", nullable = false)
    private Batch batch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quality_inspection_id", nullable = false)
    private QualityInspection qualityInspection;

    @Column(nullable = false)
    private String approvedBy;

    @Column(nullable = false)
    private LocalDateTime releaseDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BatchReleaseStatus status;

    @Column(length = 1000)
    private String remarks;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (releaseDate == null) {
            releaseDate = LocalDateTime.now();
        }

        if (status == null) {
            status = BatchReleaseStatus.PENDING;
        }

    }

    @PreUpdate
    public void onUpdate() {

        updatedAt = LocalDateTime.now();

    }

}