package com.anandh.mes.entity;

import java.time.LocalDateTime;

import com.anandh.mes.enums.EquipmentAssignmentStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "equipment_assignments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipmentAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long equipmentAssignmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id", nullable = false)
    private Batch batch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EquipmentAssignmentStatus status;

    @Column(nullable = false)
    private LocalDateTime assignmentTime;

    private LocalDateTime releaseTime;

    private String operatorName;

    @Column(length = 1000)
    private String remarks;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (assignmentTime == null) {
            assignmentTime = LocalDateTime.now();
        }

        if (status == null) {
            status = EquipmentAssignmentStatus.ASSIGNED;
        }

    }

    @PreUpdate
    public void onUpdate() {

        updatedAt = LocalDateTime.now();

    }

}