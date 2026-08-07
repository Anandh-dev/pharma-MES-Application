package com.anandh.mes.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.anandh.mes.dto.RecipeStepDTO;
import com.anandh.mes.enums.RecipeStepType;
import com.anandh.mes.service.RecipeStepService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/recipe-steps")
@RequiredArgsConstructor
public class RecipeStepController {

    private final RecipeStepService recipeStepService;

    // ==========================================================
    // CRUD
    // ==========================================================

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecipeStepDTO createRecipeStep(
            @Valid @RequestBody RecipeStepDTO recipeStepDTO) {

        return recipeStepService.createRecipeStep(recipeStepDTO);
    }

    @GetMapping
    public List<RecipeStepDTO> getAllRecipeSteps() {

        return recipeStepService.getAllRecipeSteps();
    }

    @GetMapping("/{id}")
    public RecipeStepDTO getRecipeStepById(
            @PathVariable Long id) {

        return recipeStepService.getRecipeStepById(id);
    }

    @PutMapping("/{id}")
    public RecipeStepDTO updateRecipeStep(
            @PathVariable Long id,
            @Valid @RequestBody RecipeStepDTO recipeStepDTO) {

        return recipeStepService.updateRecipeStep(id, recipeStepDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecipeStep(
            @PathVariable Long id) {

        recipeStepService.deleteRecipeStep(id);
    }

    // ==========================================================
    // SEARCH
    // ==========================================================

    @GetMapping("/recipe/{recipeId}")
    public List<RecipeStepDTO> getRecipeSteps(
            @PathVariable Long recipeId) {

        return recipeStepService.getRecipeSteps(recipeId);
    }

    @GetMapping("/type/{stepType}")
    public List<RecipeStepDTO> getByStepType(
            @PathVariable RecipeStepType stepType) {

        return recipeStepService.getByStepType(stepType);
    }

    @GetMapping("/critical")
    public List<RecipeStepDTO> getCriticalSteps() {

        return recipeStepService.getCriticalSteps();
    }

    @GetMapping("/equipment/{equipmentName}")
    public List<RecipeStepDTO> getByEquipment(
            @PathVariable String equipmentName) {

        return recipeStepService.getByEquipment(equipmentName);
    }

    @GetMapping("/page")
    public Page<RecipeStepDTO> getRecipeStepPage(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "recipeStepId")
            String sortBy) {

        return recipeStepService.getRecipeStepPage(
                page,
                size,
                sortBy);
    }

    // ==========================================================
    // STEP MANAGEMENT
    // ==========================================================

    @PutMapping("/{id}/move-up")
    public RecipeStepDTO moveStepUp(
            @PathVariable Long id) {

        return recipeStepService.moveStepUp(id);
    }

    @PutMapping("/{id}/move-down")
    public RecipeStepDTO moveStepDown(
            @PathVariable Long id) {

        return recipeStepService.moveStepDown(id);
    }

    @PutMapping("/{id}/mark-critical")
    public RecipeStepDTO markCritical(
            @PathVariable Long id) {

        return recipeStepService.markCritical(id);
    }

    @PutMapping("/{id}/unmark-critical")
    public RecipeStepDTO unmarkCritical(
            @PathVariable Long id) {

        return recipeStepService.unmarkCritical(id);
    }

}