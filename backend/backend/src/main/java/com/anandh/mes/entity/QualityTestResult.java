package com.anandh.mes.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "quality_test_results")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QualityTestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qualityTestResultId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quality_inspection_id", nullable = false)
    private QualityInspection qualityInspection;

    @Column(nullable = false)
    private String testName;

    @Column(nullable = false)
    private String expectedValue;

    @Column(nullable = false)
    private String actualValue;

    @Column(nullable = false)
    private String unit;

    @Column(nullable = false)
    private Boolean passed;

    @Column(length = 1000)
    private String remarks;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (passed == null) {
            passed = false;
        }

    }

    @PreUpdate
    public void onUpdate() {

        updatedAt = LocalDateTime.now();

    }

}