package com.anandh.mes.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anandh.mes.dto.RecipeParameterDTO;
import com.anandh.mes.entity.RecipeParameter;
import com.anandh.mes.entity.RecipeStep;
import com.anandh.mes.exception.ResourceNotFoundException;
import com.anandh.mes.repository.RecipeParameterRepository;
import com.anandh.mes.repository.RecipeStepRepository;
import com.anandh.mes.service.RecipeParameterService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipeParameterServiceImpl implements RecipeParameterService {

    private final RecipeParameterRepository recipeParameterRepository;

    private final RecipeStepRepository recipeStepRepository;

    // ==========================================================
    // CREATE
    // ==========================================================

    @Override
    public RecipeParameterDTO createRecipeParameter(
            RecipeParameterDTO dto) {

        RecipeStep recipeStep = recipeStepRepository
                .findById(dto.getRecipeStepId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Recipe Step not found"));

        RecipeParameter parameter = mapToEntity(dto);

        parameter.setRecipeStep(recipeStep);

        RecipeParameter saved =
                recipeParameterRepository.save(parameter);

        return mapToDTO(saved);
    }

    // ==========================================================
    // READ
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<RecipeParameterDTO> getAllRecipeParameters() {

        return recipeParameterRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RecipeParameterDTO getRecipeParameterById(Long id) {

        RecipeParameter parameter =
                recipeParameterRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Recipe Parameter not found"));

        return mapToDTO(parameter);
    }

    // ==========================================================
    // UPDATE
    // ==========================================================

    @Override
    public RecipeParameterDTO updateRecipeParameter(
            Long id,
            RecipeParameterDTO dto) {

        RecipeParameter parameter =
                recipeParameterRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Recipe Parameter not found"));

        RecipeStep recipeStep =
                recipeStepRepository.findById(dto.getRecipeStepId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Recipe Step not found"));

        parameter.setRecipeStep(recipeStep);
        parameter.setParameterName(dto.getParameterName());
        parameter.setParameterValue(dto.getParameterValue());
        parameter.setUnit(dto.getUnit());
        parameter.setMinimumValue(dto.getMinimumValue());
        parameter.setMaximumValue(dto.getMaximumValue());
        parameter.setMandatory(dto.getMandatory());
        parameter.setRemarks(dto.getRemarks());

        RecipeParameter updated =
                recipeParameterRepository.save(parameter);

        return mapToDTO(updated);
    }

    // ==========================================================
    // DELETE
    // ==========================================================

    @Override
    public void deleteRecipeParameter(Long id) {

        RecipeParameter parameter =
                recipeParameterRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Recipe Parameter not found"));

        recipeParameterRepository.delete(parameter);
    }

    // ==========================================================
    // SEARCH
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<RecipeParameterDTO> getParametersByRecipeStep(
            Long recipeStepId) {

        return recipeParameterRepository
                .findByRecipeStepRecipeStepId(recipeStepId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeParameterDTO> searchByParameterName(
            String parameterName) {

        return recipeParameterRepository
                .findByParameterNameContainingIgnoreCase(parameterName)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeParameterDTO> getMandatoryParameters() {

        return recipeParameterRepository
                .findByMandatory(true)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeParameterDTO> getOptionalParameters(
            Long recipeStepId) {

        return recipeParameterRepository
                .findByRecipeStepRecipeStepIdAndMandatory(
                        recipeStepId,
                        false)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // PAGINATION
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public Page<RecipeParameterDTO> getRecipeParameterPage(
            int page,
            int size,
            String sortBy) {

        Pageable pageable =
                PageRequest.of(page, size, Sort.by(sortBy));

        return recipeParameterRepository
                .findAll(pageable)
                .map(this::mapToDTO);
    }

    // ==========================================================
    // PARAMETER MANAGEMENT
    // ==========================================================
    @Override
    public RecipeParameterDTO markMandatory(Long recipeParameterId) {

        RecipeParameter parameter = recipeParameterRepository.findById(recipeParameterId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Recipe Parameter not found"));

        parameter.setMandatory(true);

        return mapToDTO(recipeParameterRepository.save(parameter));
    }

    @Override
    public RecipeParameterDTO markOptional(Long recipeParameterId) {

        RecipeParameter parameter = recipeParameterRepository.findById(recipeParameterId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Recipe Parameter not found"));

        parameter.setMandatory(false);

        return mapToDTO(recipeParameterRepository.save(parameter));
    }

    @Override
    public RecipeParameterDTO updateParameterValue(
            Long recipeParameterId,
            String parameterValue) {

        RecipeParameter parameter = recipeParameterRepository.findById(recipeParameterId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Recipe Parameter not found"));

        parameter.setParameterValue(parameterValue);

        return mapToDTO(recipeParameterRepository.save(parameter));
    }

    // ==========================================================
    // DTO MAPPING
    // ==========================================================

    private RecipeParameterDTO mapToDTO(RecipeParameter parameter) {

        return RecipeParameterDTO.builder()
                .recipeParameterId(parameter.getRecipeParameterId())
                .recipeStepId(parameter.getRecipeStep().getRecipeStepId())
                .parameterName(parameter.getParameterName())
                .parameterValue(parameter.getParameterValue())
                .unit(parameter.getUnit())
                .minimumValue(parameter.getMinimumValue())
                .maximumValue(parameter.getMaximumValue())
                .mandatory(parameter.getMandatory())
                .remarks(parameter.getRemarks())
                .build();
    }

    private RecipeParameter mapToEntity(RecipeParameterDTO dto) {

        return RecipeParameter.builder()
                .parameterName(dto.getParameterName())
                .parameterValue(dto.getParameterValue())
                .unit(dto.getUnit())
                .minimumValue(dto.getMinimumValue())
                .maximumValue(dto.getMaximumValue())
                .mandatory(
                        dto.getMandatory() == null
                                ? true
                                : dto.getMandatory())
                .remarks(dto.getRemarks())
                .build();
    }

}