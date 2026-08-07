package com.anandh.mes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.anandh.mes.entity.Recipe;
import com.anandh.mes.enums.RecipeStatus;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Optional<Recipe> findByRecipeCode(String recipeCode);

    List<Recipe> findByRecipeNameContainingIgnoreCase(String recipeName);

    List<Recipe> findByStatus(RecipeStatus status);

    List<Recipe> findByMaterialMaterialId(Long materialId);

    Optional<Recipe> findByRecipeCodeAndVersion(
            String recipeCode,
            Integer version);

    List<Recipe> findByRecipeCodeOrderByVersionDesc(
            String recipeCode);

    Page<Recipe> findAll(Pageable pageable);

}