package com.anandh.mes.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "recipe_parameters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeParameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeParameterId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_step_id", nullable = false)
    private RecipeStep recipeStep;

    @Column(nullable = false)
    private String parameterName;

    @Column(nullable = false)
    private String parameterValue;

    @Column(nullable = false)
    private String unit;

    private Double minimumValue;

    private Double maximumValue;

    @Column(nullable = false)
    private Boolean mandatory;

    @Column(length = 1000)
    private String remarks;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (mandatory == null) {
            mandatory = true;
        }

    }

    @PreUpdate
    public void onUpdate() {

        updatedAt = LocalDateTime.now();

    }

}