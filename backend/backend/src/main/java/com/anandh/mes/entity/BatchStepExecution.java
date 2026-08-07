package com.anandh.mes.entity;

import java.time.LocalDateTime;

import com.anandh.mes.enums.BatchStepStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "batch_step_executions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchStepExecution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchStepExecutionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id", nullable = false)
    private Batch batch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_step_id", nullable = false)
    private RecipeStep recipeStep;

    @Column(nullable = false)
    private Integer stepNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BatchStepStatus status;

    private String operatorName;

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
            status = BatchStepStatus.WAITING;
        }

    }

    @PreUpdate
    public void onUpdate() {

        updatedAt = LocalDateTime.now();

    }

}