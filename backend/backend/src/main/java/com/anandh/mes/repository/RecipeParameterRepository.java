package com.anandh.mes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.anandh.mes.entity.RecipeParameter;

public interface RecipeParameterRepository
        extends JpaRepository<RecipeParameter, Long> {

    List<RecipeParameter> findByRecipeStepRecipeStepId(
            Long recipeStepId);

    List<RecipeParameter> findByParameterNameContainingIgnoreCase(
            String parameterName);

    List<RecipeParameter> findByMandatory(
            Boolean mandatory);

    List<RecipeParameter> findByRecipeStepRecipeStepIdAndMandatory(
            Long recipeStepId,
            Boolean mandatory);

    Page<RecipeParameter> findAll(
            Pageable pageable);

}