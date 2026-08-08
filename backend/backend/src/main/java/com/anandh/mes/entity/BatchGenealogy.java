package com.anandh.mes.entity;

import java.time.LocalDateTime;

import com.anandh.mes.enums.GenealogyType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "batch_genealogy")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchGenealogy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchGenealogyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_batch_id", nullable = false)
    private Batch parentBatch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_batch_id", nullable = false)
    private Batch childBatch;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GenealogyType relationshipType;

    @Column(nullable = false)
    private Double quantity;

    @Column(nullable = false)
    private String unit;

    @Column(length = 1000)
    private String remarks;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

    }

    @PreUpdate
    public void onUpdate() {

        updatedAt = LocalDateTime.now();

    }

}