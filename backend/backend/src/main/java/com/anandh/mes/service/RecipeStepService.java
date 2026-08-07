package com.anandh.mes.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.anandh.mes.dto.RecipeStepDTO;
import com.anandh.mes.enums.RecipeStepType;

public interface RecipeStepService {

    // ==========================================================
    // CRUD
    // ==========================================================

    RecipeStepDTO createRecipeStep(
            RecipeStepDTO recipeStepDTO);

    List<RecipeStepDTO> getAllRecipeSteps();

    RecipeStepDTO getRecipeStepById(
            Long id);

    RecipeStepDTO updateRecipeStep(
            Long id,
            RecipeStepDTO recipeStepDTO);

    void deleteRecipeStep(
            Long id);

    // ==========================================================
    // SEARCH
    // ==========================================================

    List<RecipeStepDTO> getRecipeSteps(
            Long recipeId);

    List<RecipeStepDTO> getByStepType(
            RecipeStepType stepType);

    List<RecipeStepDTO> getCriticalSteps();

    List<RecipeStepDTO> getByEquipment(
            String equipmentName);

    Page<RecipeStepDTO> getRecipeStepPage(
            int page,
            int size,
            String sortBy);

    // ==========================================================
    // STEP MANAGEMENT
    // ==========================================================

    RecipeStepDTO moveStepUp(
            Long recipeStepId);

    RecipeStepDTO moveStepDown(
            Long recipeStepId);

    RecipeStepDTO markCritical(
            Long recipeStepId);

    RecipeStepDTO unmarkCritical(
            Long recipeStepId);

}