package com.anandh.mes.entity;

import java.time.LocalDateTime;

import com.anandh.mes.enums.AuditAction;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "audit_trails")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditTrail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditTrailId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuditAction action;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String entityName;

    @Column(nullable = false)
    private Long entityId;

    private String fieldName;

    @Column(length = 2000)
    private String oldValue;

    @Column(length = 2000)
    private String newValue;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    private String ipAddress;

    @Column(length = 1000)
    private String remarks;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }

    }

    @PreUpdate
    public void onUpdate() {

        updatedAt = LocalDateTime.now();

    }

}