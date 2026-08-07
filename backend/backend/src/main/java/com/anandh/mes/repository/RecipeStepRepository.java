package com.anandh.mes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.anandh.mes.entity.RecipeStep;
import com.anandh.mes.enums.RecipeStepType;

public interface RecipeStepRepository extends JpaRepository<RecipeStep, Long> {

    List<RecipeStep> findByRecipeRecipeIdOrderByStepNumberAsc(
            Long recipeId);

    List<RecipeStep> findByStepType(
            RecipeStepType stepType);

    List<RecipeStep> findByCriticalStep(
            Boolean criticalStep);

    List<RecipeStep> findByEquipmentNameIgnoreCase(
            String equipmentName);

    Page<RecipeStep> findAll(
            Pageable pageable);

}