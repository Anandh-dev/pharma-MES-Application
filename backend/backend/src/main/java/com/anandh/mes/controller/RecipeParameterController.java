package com.anandh.mes.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.anandh.mes.dto.RecipeParameterDTO;
import com.anandh.mes.service.RecipeParameterService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/recipe-parameters")
@RequiredArgsConstructor
public class RecipeParameterController {

    private final RecipeParameterService recipeParameterService;

    // ==========================================================
    // CRUD
    // ==========================================================

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecipeParameterDTO createRecipeParameter(
            @Valid @RequestBody RecipeParameterDTO recipeParameterDTO) {

        return recipeParameterService.createRecipeParameter(recipeParameterDTO);
    }

    @GetMapping
    public List<RecipeParameterDTO> getAllRecipeParameters() {

        return recipeParameterService.getAllRecipeParameters();
    }

    @GetMapping("/{id}")
    public RecipeParameterDTO getRecipeParameterById(
            @PathVariable Long id) {

        return recipeParameterService.getRecipeParameterById(id);
    }

    @PutMapping("/{id}")
    public RecipeParameterDTO updateRecipeParameter(
            @PathVariable Long id,
            @Valid @RequestBody RecipeParameterDTO recipeParameterDTO) {

        return recipeParameterService.updateRecipeParameter(id, recipeParameterDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecipeParameter(
            @PathVariable Long id) {

        recipeParameterService.deleteRecipeParameter(id);
    }

    // ==========================================================
    // SEARCH
    // ==========================================================

    @GetMapping("/recipe-step/{recipeStepId}")
    public List<RecipeParameterDTO> getParametersByRecipeStep(
            @PathVariable Long recipeStepId) {

        return recipeParameterService.getParametersByRecipeStep(recipeStepId);
    }

    @GetMapping("/search")
    public List<RecipeParameterDTO> searchByParameterName(
            @RequestParam String parameterName) {

        return recipeParameterService.searchByParameterName(parameterName);
    }

    @GetMapping("/mandatory")
    public List<RecipeParameterDTO> getMandatoryParameters() {

        return recipeParameterService.getMandatoryParameters();
    }

    @GetMapping("/optional/{recipeStepId}")
    public List<RecipeParameterDTO> getOptionalParameters(
            @PathVariable Long recipeStepId) {

        return recipeParameterService.getOptionalParameters(recipeStepId);
    }

    @GetMapping("/page")
    public Page<RecipeParameterDTO> getRecipeParameterPage(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "recipeParameterId")
            String sortBy) {

        return recipeParameterService.getRecipeParameterPage(
                page,
                size,
                sortBy);
    }

    // ==========================================================
    // PARAMETER MANAGEMENT
    // ==========================================================

    @PutMapping("/{id}/mandatory")
    public RecipeParameterDTO markMandatory(
            @PathVariable Long id) {

        return recipeParameterService.markMandatory(id);
    }

    @PutMapping("/{id}/optional")
    public RecipeParameterDTO markOptional(
            @PathVariable Long id) {

        return recipeParameterService.markOptional(id);
    }

    @PatchMapping("/{id}/value")
    public RecipeParameterDTO updateParameterValue(
            @PathVariable Long id,
            @RequestParam String parameterValue) {

        return recipeParameterService.updateParameterValue(
                id,
                parameterValue);
    }

}