package com.anandh.mes.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.anandh.mes.dto.RecipeDTO;
import com.anandh.mes.enums.RecipeStatus;

public interface RecipeService {

    // ==========================================================
    // CRUD
    // ==========================================================

    RecipeDTO createRecipe(
            RecipeDTO recipeDTO);

    List<RecipeDTO> getAllRecipes();

    RecipeDTO getRecipeById(
            Long id);

    RecipeDTO updateRecipe(
            Long id,
            RecipeDTO recipeDTO);

    void deleteRecipe(
            Long id);

    // ==========================================================
    // SEARCH
    // ==========================================================

    RecipeDTO getByRecipeCode(
            String recipeCode);

    List<RecipeDTO> searchByRecipeName(
            String recipeName);

    List<RecipeDTO> getByStatus(
            RecipeStatus status);

    List<RecipeDTO> getByMaterial(
            Long materialId);

    RecipeDTO getRecipeVersion(
            String recipeCode,
            Integer version);

    List<RecipeDTO> getRecipeVersions(
            String recipeCode);

    Page<RecipeDTO> getRecipePage(
            int page,
            int size,
            String sortBy);

    // ==========================================================
    // RECIPE WORKFLOW
    // ==========================================================

    RecipeDTO submitForReview(
            Long recipeId);

    RecipeDTO approveRecipe(
            Long recipeId,
            String approvedBy);

    RecipeDTO activateRecipe(
            Long recipeId);

    RecipeDTO markObsolete(
            Long recipeId);

    RecipeDTO createNewVersion(
            Long recipeId);

}