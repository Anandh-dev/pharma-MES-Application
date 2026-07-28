package com.anandh.mes.entity;

import java.time.LocalDateTime;

import com.anandh.mes.enums.EquipmentStatus;
import com.anandh.mes.enums.EquipmentType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "equipment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long equipmentId;

    @Column(nullable = false, unique = true)
    private String equipmentCode;

    @Column(nullable = false)
    private String equipmentName;

    @Enumerated(EnumType.STRING)
    private EquipmentType equipmentType;

    @Enumerated(EnumType.STRING)
    private EquipmentStatus status;

    private String location;

    private String manufacturer;

    private String model;

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