package com.anandh.mes.entity;

import java.time.LocalDateTime;

import com.anandh.mes.enums.DeviationSeverity;
import com.anandh.mes.enums.DeviationStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "deviations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Deviation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deviationId;

    @Column(nullable = false, unique = true)
    private String deviationNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id", nullable = false)
    private Batch batch;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeviationSeverity severity;

    @Column(nullable = false, length = 2000)
    private String description;

    @Column(length = 2000)
    private String rootCause;

    @Column(length = 2000)
    private String correctiveAction;

    @Column(length = 2000)
    private String preventiveAction;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeviationStatus status;

    @Column(nullable = false)
    private String reportedBy;

    @Column(nullable = false)
    private LocalDateTime reportedDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (reportedDate == null) {
            reportedDate = LocalDateTime.now();
        }

        if (status == null) {
            status = DeviationStatus.OPEN;
        }

        if (severity == null) {
            severity = DeviationSeverity.LOW;
        }

    }

    @PreUpdate
    public void onUpdate() {

        updatedAt = LocalDateTime.now();

    }

}