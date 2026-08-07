package com.anandh.mes.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anandh.mes.dto.RecipeStepDTO;
import com.anandh.mes.entity.Recipe;
import com.anandh.mes.entity.RecipeStep;
import com.anandh.mes.enums.RecipeStepType;
import com.anandh.mes.exception.ResourceNotFoundException;
import com.anandh.mes.repository.RecipeRepository;
import com.anandh.mes.repository.RecipeStepRepository;
import com.anandh.mes.service.RecipeStepService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipeStepServiceImpl implements RecipeStepService {

    private final RecipeStepRepository recipeStepRepository;
    private final RecipeRepository recipeRepository;

    // ==========================================================
    // CREATE
    // ==========================================================

    @Override
    public RecipeStepDTO createRecipeStep(RecipeStepDTO dto) {

        Recipe recipe = recipeRepository.findById(dto.getRecipeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Recipe not found"));

        RecipeStep recipeStep = mapToEntity(dto);
        recipeStep.setRecipe(recipe);

        RecipeStep saved = recipeStepRepository.save(recipeStep);

        return mapToDTO(saved);
    }

    // ==========================================================
    // READ
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<RecipeStepDTO> getAllRecipeSteps() {

        return recipeStepRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RecipeStepDTO getRecipeStepById(Long id) {

        RecipeStep recipeStep = recipeStepRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Recipe Step not found"));

        return mapToDTO(recipeStep);
    }

    // ==========================================================
    // UPDATE
    // ==========================================================

    @Override
    public RecipeStepDTO updateRecipeStep(Long id,
                                          RecipeStepDTO dto) {

        RecipeStep recipeStep = recipeStepRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Recipe Step not found"));

        Recipe recipe = recipeRepository.findById(dto.getRecipeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Recipe not found"));

        recipeStep.setRecipe(recipe);
        recipeStep.setStepNumber(dto.getStepNumber());
        recipeStep.setStepName(dto.getStepName());
        recipeStep.setStepType(dto.getStepType());
        recipeStep.setDescription(dto.getDescription());
        recipeStep.setEstimatedDuration(dto.getEstimatedDuration());
        recipeStep.setEquipmentName(dto.getEquipmentName());
        recipeStep.setCriticalStep(dto.getCriticalStep());

        RecipeStep updated = recipeStepRepository.save(recipeStep);

        return mapToDTO(updated);
    }

    // ==========================================================
    // DELETE
    // ==========================================================

    @Override
    public void deleteRecipeStep(Long id) {

        RecipeStep recipeStep = recipeStepRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Recipe Step not found"));

        recipeStepRepository.delete(recipeStep);
    }

    // ==========================================================
    // SEARCH
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<RecipeStepDTO> getRecipeSteps(Long recipeId) {

        return recipeStepRepository
                .findByRecipeRecipeIdOrderByStepNumberAsc(recipeId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeStepDTO> getByStepType(RecipeStepType stepType) {

        return recipeStepRepository.findByStepType(stepType)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeStepDTO> getCriticalSteps() {

        return recipeStepRepository.findByCriticalStep(true)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeStepDTO> getByEquipment(String equipmentName) {

        return recipeStepRepository.findByEquipmentNameIgnoreCase(equipmentName)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // PAGINATION
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public Page<RecipeStepDTO> getRecipeStepPage(
            int page,
            int size,
            String sortBy) {

        Pageable pageable =
                PageRequest.of(page, size, Sort.by(sortBy));

        return recipeStepRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    // ==========================================================
    // STEP MANAGEMENT
    // ==========================================================
    @Override
    public RecipeStepDTO moveStepUp(Long recipeStepId) {

        RecipeStep currentStep = recipeStepRepository.findById(recipeStepId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Recipe Step not found"));

        List<RecipeStep> steps = recipeStepRepository
                .findByRecipeRecipeIdOrderByStepNumberAsc(
                        currentStep.getRecipe().getRecipeId());

        for (int i = 1; i < steps.size(); i++) {

            if (steps.get(i).getRecipeStepId().equals(recipeStepId)) {

                RecipeStep previous = steps.get(i - 1);

                Integer temp = previous.getStepNumber();
                previous.setStepNumber(currentStep.getStepNumber());
                currentStep.setStepNumber(temp);

                recipeStepRepository.save(previous);
                recipeStepRepository.save(currentStep);

                break;
            }
        }

        return mapToDTO(currentStep);
    }

    @Override
    public RecipeStepDTO moveStepDown(Long recipeStepId) {

        RecipeStep currentStep = recipeStepRepository.findById(recipeStepId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Recipe Step not found"));

        List<RecipeStep> steps = recipeStepRepository
                .findByRecipeRecipeIdOrderByStepNumberAsc(
                        currentStep.getRecipe().getRecipeId());

        for (int i = 0; i < steps.size() - 1; i++) {

            if (steps.get(i).getRecipeStepId().equals(recipeStepId)) {

                RecipeStep next = steps.get(i + 1);

                Integer temp = next.getStepNumber();
                next.setStepNumber(currentStep.getStepNumber());
                currentStep.setStepNumber(temp);

                recipeStepRepository.save(next);
                recipeStepRepository.save(currentStep);

                break;
            }
        }

        return mapToDTO(currentStep);
    }

    @Override
    public RecipeStepDTO markCritical(Long recipeStepId) {

        RecipeStep recipeStep = recipeStepRepository.findById(recipeStepId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Recipe Step not found"));

        recipeStep.setCriticalStep(true);

        return mapToDTO(recipeStepRepository.save(recipeStep));
    }

    @Override
    public RecipeStepDTO unmarkCritical(Long recipeStepId) {

        RecipeStep recipeStep = recipeStepRepository.findById(recipeStepId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Recipe Step not found"));

        recipeStep.setCriticalStep(false);

        return mapToDTO(recipeStepRepository.save(recipeStep));
    }

    // ==========================================================
    // DTO MAPPING
    // ==========================================================

    private RecipeStepDTO mapToDTO(RecipeStep recipeStep) {

        return RecipeStepDTO.builder()
                .recipeStepId(recipeStep.getRecipeStepId())
                .recipeId(recipeStep.getRecipe().getRecipeId())
                .stepNumber(recipeStep.getStepNumber())
                .stepName(recipeStep.getStepName())
                .stepType(recipeStep.getStepType())
                .description(recipeStep.getDescription())
                .estimatedDuration(recipeStep.getEstimatedDuration())
                .equipmentName(recipeStep.getEquipmentName())
                .criticalStep(recipeStep.getCriticalStep())
                .build();
    }

    private RecipeStep mapToEntity(RecipeStepDTO dto) {

        return RecipeStep.builder()
                .stepNumber(dto.getStepNumber())
                .stepName(dto.getStepName())
                .stepType(dto.getStepType())
                .description(dto.getDescription())
                .estimatedDuration(dto.getEstimatedDuration())
                .equipmentName(dto.getEquipmentName())
                .criticalStep(
                        dto.getCriticalStep() == null
                                ? false
                                : dto.getCriticalStep())
                .build();
    }

}