package com.anandh.mes.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anandh.mes.dto.RecipeDTO;
import com.anandh.mes.entity.Material;
import com.anandh.mes.entity.Recipe;
import com.anandh.mes.enums.RecipeStatus;
import com.anandh.mes.exception.ResourceNotFoundException;
import com.anandh.mes.repository.MaterialRepository;
import com.anandh.mes.repository.RecipeRepository;
import com.anandh.mes.service.RecipeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    private final MaterialRepository materialRepository;

    // ==========================================================
    // CREATE
    // ==========================================================

    @Override
    public RecipeDTO createRecipe(RecipeDTO dto) {

        Material material = materialRepository.findById(dto.getMaterialId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Material not found"));

        Recipe recipe = mapToEntity(dto);

        recipe.setMaterial(material);
        recipe.setStatus(RecipeStatus.DRAFT);

        Recipe savedRecipe = recipeRepository.save(recipe);

        return mapToDTO(savedRecipe);
    }

    // ==========================================================
    // READ
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<RecipeDTO> getAllRecipes() {

        return recipeRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RecipeDTO getRecipeById(Long id) {

        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Recipe not found"));

        return mapToDTO(recipe);
    }

    // ==========================================================
    // UPDATE
    // ==========================================================

    @Override
    public RecipeDTO updateRecipe(
            Long id,
            RecipeDTO dto) {

        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Recipe not found"));

        Material material = materialRepository.findById(dto.getMaterialId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Material not found"));

        recipe.setRecipeCode(dto.getRecipeCode());
        recipe.setRecipeName(dto.getRecipeName());
        recipe.setMaterial(material);
        recipe.setDescription(dto.getDescription());

        Recipe updatedRecipe = recipeRepository.save(recipe);

        return mapToDTO(updatedRecipe);
    }

    // ==========================================================
    // DELETE
    // ==========================================================

    @Override
    public void deleteRecipe(Long id) {

        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Recipe not found"));

        recipeRepository.delete(recipe);
    }

    // ==========================================================
    // SEARCH
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public RecipeDTO getByRecipeCode(String recipeCode) {

        return mapToDTO(
                recipeRepository.findByRecipeCode(recipeCode)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Recipe not found")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeDTO> searchByRecipeName(String recipeName) {

        return recipeRepository.findByRecipeNameContainingIgnoreCase(recipeName)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeDTO> getByStatus(RecipeStatus status) {

        return recipeRepository.findByStatus(status)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeDTO> getByMaterial(Long materialId) {

        return recipeRepository.findByMaterialMaterialId(materialId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RecipeDTO getRecipeVersion(
            String recipeCode,
            Integer version) {

        return mapToDTO(
                recipeRepository.findByRecipeCodeAndVersion(
                                recipeCode,
                                version)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Recipe version not found")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeDTO> getRecipeVersions(String recipeCode) {

        return recipeRepository
                .findByRecipeCodeOrderByVersionDesc(recipeCode)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // PAGINATION
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public Page<RecipeDTO> getRecipePage(
            int page,
            int size,
            String sortBy) {

        Pageable pageable =
                PageRequest.of(page, size, Sort.by(sortBy));

        return recipeRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    // ==========================================================
    // RECIPE WORKFLOW
    // ==========================================================

    @Override
    public RecipeDTO submitForReview(Long recipeId) {

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Recipe not found"));

        recipe.setStatus(RecipeStatus.UNDER_REVIEW);

        return mapToDTO(recipeRepository.save(recipe));
    }
    @Override
    public RecipeDTO approveRecipe(
            Long recipeId,
            String approvedBy) {

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Recipe not found"));

        recipe.setStatus(RecipeStatus.APPROVED);
        recipe.setApprovedBy(approvedBy);
        recipe.setApprovedAt(LocalDateTime.now());

        return mapToDTO(recipeRepository.save(recipe));
    }

    @Override
    public RecipeDTO activateRecipe(Long recipeId) {

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Recipe not found"));

        // Mark existing ACTIVE version as OBSOLETE
        List<Recipe> recipes =
                recipeRepository.findByRecipeCodeOrderByVersionDesc(
                        recipe.getRecipeCode());

        recipes.stream()
                .filter(r -> r.getStatus() == RecipeStatus.ACTIVE)
                .forEach(r -> {
                    r.setStatus(RecipeStatus.OBSOLETE);
                    recipeRepository.save(r);
                });

        recipe.setStatus(RecipeStatus.ACTIVE);

        return mapToDTO(recipeRepository.save(recipe));
    }

    @Override
    public RecipeDTO markObsolete(Long recipeId) {

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Recipe not found"));

        recipe.setStatus(RecipeStatus.OBSOLETE);

        return mapToDTO(recipeRepository.save(recipe));
    }

    @Override
    public RecipeDTO createNewVersion(Long recipeId) {

        Recipe existingRecipe = recipeRepository.findById(recipeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Recipe not found"));

        Recipe newVersion = Recipe.builder()
                .recipeCode(existingRecipe.getRecipeCode())
                .recipeName(existingRecipe.getRecipeName())
                .version(existingRecipe.getVersion() + 1)
                .material(existingRecipe.getMaterial())
                .status(RecipeStatus.DRAFT)
                .description(existingRecipe.getDescription())
                .build();

        return mapToDTO(recipeRepository.save(newVersion));
    }

    // ==========================================================
    // DTO MAPPING
    // ==========================================================

    private RecipeDTO mapToDTO(Recipe recipe) {

        return RecipeDTO.builder()
                .recipeId(recipe.getRecipeId())
                .recipeCode(recipe.getRecipeCode())
                .recipeName(recipe.getRecipeName())
                .version(recipe.getVersion())
                .materialId(recipe.getMaterial().getMaterialId())
                .status(recipe.getStatus())
                .description(recipe.getDescription())
                .approvedAt(recipe.getApprovedAt())
                .approvedBy(recipe.getApprovedBy())
                .build();
    }

    private Recipe mapToEntity(RecipeDTO dto) {

        return Recipe.builder()
                .recipeCode(dto.getRecipeCode())
                .recipeName(dto.getRecipeName())
                .version(dto.getVersion() == null ? 1 : dto.getVersion())
                .status(dto.getStatus())
                .description(dto.getDescription())
                .approvedAt(dto.getApprovedAt())
                .approvedBy(dto.getApprovedBy())
                .build();
    }

}