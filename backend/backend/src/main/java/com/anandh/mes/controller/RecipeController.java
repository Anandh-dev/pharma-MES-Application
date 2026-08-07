package com.anandh.mes.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.anandh.mes.dto.RecipeDTO;
import com.anandh.mes.enums.RecipeStatus;
import com.anandh.mes.service.RecipeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    // ==========================================================
    // CRUD
    // ==========================================================

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecipeDTO createRecipe(
            @Valid @RequestBody RecipeDTO recipeDTO) {

        return recipeService.createRecipe(recipeDTO);
    }

    @GetMapping
    public List<RecipeDTO> getAllRecipes() {

        return recipeService.getAllRecipes();
    }

    @GetMapping("/{id}")
    public RecipeDTO getRecipeById(
            @PathVariable Long id) {

        return recipeService.getRecipeById(id);
    }

    @PutMapping("/{id}")
    public RecipeDTO updateRecipe(
            @PathVariable Long id,
            @Valid @RequestBody RecipeDTO recipeDTO) {

        return recipeService.updateRecipe(id, recipeDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecipe(
            @PathVariable Long id) {

        recipeService.deleteRecipe(id);
    }

    // ==========================================================
    // SEARCH
    // ==========================================================

    @GetMapping("/code/{recipeCode}")
    public RecipeDTO getByRecipeCode(
            @PathVariable String recipeCode) {

        return recipeService.getByRecipeCode(recipeCode);
    }

    @GetMapping("/name/{recipeName}")
    public List<RecipeDTO> searchByRecipeName(
            @PathVariable String recipeName) {

        return recipeService.searchByRecipeName(recipeName);
    }

    @GetMapping("/status/{status}")
    public List<RecipeDTO> getByStatus(
            @PathVariable RecipeStatus status) {

        return recipeService.getByStatus(status);
    }

    @GetMapping("/material/{materialId}")
    public List<RecipeDTO> getByMaterial(
            @PathVariable Long materialId) {

        return recipeService.getByMaterial(materialId);
    }

    @GetMapping("/{recipeCode}/versions")
    public List<RecipeDTO> getRecipeVersions(
            @PathVariable String recipeCode) {

        return recipeService.getRecipeVersions(recipeCode);
    }

    @GetMapping("/{recipeCode}/version/{version}")
    public RecipeDTO getRecipeVersion(
            @PathVariable String recipeCode,
            @PathVariable Integer version) {

        return recipeService.getRecipeVersion(recipeCode, version);
    }

    @GetMapping("/page")
    public Page<RecipeDTO> getRecipePage(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "recipeId")
            String sortBy) {

        return recipeService.getRecipePage(page, size, sortBy);
    }

    // ==========================================================
    // RECIPE WORKFLOW
    // ==========================================================

    @PutMapping("/{id}/submit")
    public RecipeDTO submitForReview(
            @PathVariable Long id) {

        return recipeService.submitForReview(id);
    }

    @PutMapping("/{id}/approve")
    public RecipeDTO approveRecipe(

            @PathVariable Long id,

            @RequestParam String approvedBy) {

        return recipeService.approveRecipe(id, approvedBy);
    }

    @PutMapping("/{id}/activate")
    public RecipeDTO activateRecipe(
            @PathVariable Long id) {

        return recipeService.activateRecipe(id);
    }

    @PutMapping("/{id}/obsolete")
    public RecipeDTO markObsolete(
            @PathVariable Long id) {

        return recipeService.markObsolete(id);
    }

    @PostMapping("/{id}/new-version")
    public RecipeDTO createNewVersion(
            @PathVariable Long id) {

        return recipeService.createNewVersion(id);
    }

}