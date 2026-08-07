package com.anandh.mes.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.anandh.mes.dto.RecipeParameterDTO;

public interface RecipeParameterService {

    // ==========================================================
    // CRUD
    // ==========================================================

    RecipeParameterDTO createRecipeParameter(
            RecipeParameterDTO recipeParameterDTO);

    List<RecipeParameterDTO> getAllRecipeParameters();

    RecipeParameterDTO getRecipeParameterById(
            Long id);

    RecipeParameterDTO updateRecipeParameter(
            Long id,
            RecipeParameterDTO recipeParameterDTO);

    void deleteRecipeParameter(
            Long id);

    // ==========================================================
    // SEARCH
    // ==========================================================

    List<RecipeParameterDTO> getParametersByRecipeStep(
            Long recipeStepId);

    List<RecipeParameterDTO> searchByParameterName(
            String parameterName);

    List<RecipeParameterDTO> getMandatoryParameters();

    List<RecipeParameterDTO> getOptionalParameters(
            Long recipeStepId);

    Page<RecipeParameterDTO> getRecipeParameterPage(
            int page,
            int size,
            String sortBy);

    // ==========================================================
    // PARAMETER MANAGEMENT
    // ==========================================================

    RecipeParameterDTO markMandatory(
            Long recipeParameterId);

    RecipeParameterDTO markOptional(
            Long recipeParameterId);

    RecipeParameterDTO updateParameterValue(
            Long recipeParameterId,
            String parameterValue);

}