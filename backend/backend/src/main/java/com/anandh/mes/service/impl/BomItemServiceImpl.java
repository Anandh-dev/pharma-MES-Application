package com.anandh.mes.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anandh.mes.dto.BomItemDTO;
import com.anandh.mes.entity.BomItem;
import com.anandh.mes.entity.Material;
import com.anandh.mes.entity.Recipe;
import com.anandh.mes.exception.ResourceNotFoundException;
import com.anandh.mes.repository.BomItemRepository;
import com.anandh.mes.repository.MaterialRepository;
import com.anandh.mes.repository.RecipeRepository;
import com.anandh.mes.service.BomItemService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BomItemServiceImpl implements BomItemService {

    private final BomItemRepository bomItemRepository;
    private final RecipeRepository recipeRepository;
    private final MaterialRepository materialRepository;

    // ==========================================================
    // CREATE
    // ==========================================================

    @Override
    public BomItemDTO createBomItem(BomItemDTO dto) {

        Recipe recipe = recipeRepository.findById(dto.getRecipeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Recipe not found"));

        Material material = materialRepository.findById(dto.getMaterialId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Material not found"));

        BomItem bomItem = mapToEntity(dto);

        bomItem.setRecipe(recipe);
        bomItem.setMaterial(material);

        BomItem saved = bomItemRepository.save(bomItem);

        return mapToDTO(saved);
    }

    // ==========================================================
    // READ
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<BomItemDTO> getAllBomItems() {

        return bomItemRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BomItemDTO getBomItemById(Long id) {

        BomItem bomItem = bomItemRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("BOM Item not found"));

        return mapToDTO(bomItem);
    }

    // ==========================================================
    // UPDATE
    // ==========================================================

    @Override
    public BomItemDTO updateBomItem(Long id,
                                    BomItemDTO dto) {

        BomItem bomItem = bomItemRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("BOM Item not found"));

        Recipe recipe = recipeRepository.findById(dto.getRecipeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Recipe not found"));

        Material material = materialRepository.findById(dto.getMaterialId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Material not found"));

        bomItem.setRecipe(recipe);
        bomItem.setMaterial(material);
        bomItem.setQuantity(dto.getQuantity());
        bomItem.setUnit(dto.getUnit());
        bomItem.setSequence(dto.getSequence());
        bomItem.setOptionalMaterial(dto.getOptionalMaterial());
        bomItem.setRemarks(dto.getRemarks());

        BomItem updated = bomItemRepository.save(bomItem);

        return mapToDTO(updated);
    }

    // ==========================================================
    // DELETE
    // ==========================================================

    @Override
    public void deleteBomItem(Long id) {

        BomItem bomItem = bomItemRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("BOM Item not found"));

        bomItemRepository.delete(bomItem);
    }

    // ==========================================================
    // SEARCH
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<BomItemDTO> getBomItemsByRecipe(Long recipeId) {

        return bomItemRepository
                .findByRecipeRecipeIdOrderBySequenceAsc(recipeId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BomItemDTO> getBomItemsByMaterial(Long materialId) {

        return bomItemRepository
                .findByMaterialMaterialId(materialId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BomItemDTO> getOptionalMaterials() {

        return bomItemRepository
                .findByOptionalMaterial(true)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BomItemDTO> getMandatoryMaterials(Long recipeId) {

        return bomItemRepository
                .findByRecipeRecipeIdAndOptionalMaterial(recipeId, false)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // PAGINATION
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public Page<BomItemDTO> getBomItemPage(
            int page,
            int size,
            String sortBy) {

        Pageable pageable =
                PageRequest.of(page, size, Sort.by(sortBy));

        return bomItemRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    // ==========================================================
    // BOM MANAGEMENT
    // ==========================================================
    @Override
    public BomItemDTO markOptional(Long bomItemId) {

        BomItem bomItem = bomItemRepository.findById(bomItemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("BOM Item not found"));

        bomItem.setOptionalMaterial(true);

        return mapToDTO(bomItemRepository.save(bomItem));
    }

    @Override
    public BomItemDTO markMandatory(Long bomItemId) {

        BomItem bomItem = bomItemRepository.findById(bomItemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("BOM Item not found"));

        bomItem.setOptionalMaterial(false);

        return mapToDTO(bomItemRepository.save(bomItem));
    }

    @Override
    public BomItemDTO moveMaterialUp(Long bomItemId) {

        BomItem current = bomItemRepository.findById(bomItemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("BOM Item not found"));

        List<BomItem> items = bomItemRepository
                .findByRecipeRecipeIdOrderBySequenceAsc(
                        current.getRecipe().getRecipeId());

        for (int i = 1; i < items.size(); i++) {

            if (items.get(i).getBomItemId().equals(bomItemId)) {

                BomItem previous = items.get(i - 1);

                Integer temp = previous.getSequence();
                previous.setSequence(current.getSequence());
                current.setSequence(temp);

                bomItemRepository.save(previous);
                bomItemRepository.save(current);

                break;
            }
        }

        return mapToDTO(current);
    }

    @Override
    public BomItemDTO moveMaterialDown(Long bomItemId) {

        BomItem current = bomItemRepository.findById(bomItemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("BOM Item not found"));

        List<BomItem> items = bomItemRepository
                .findByRecipeRecipeIdOrderBySequenceAsc(
                        current.getRecipe().getRecipeId());

        for (int i = 0; i < items.size() - 1; i++) {

            if (items.get(i).getBomItemId().equals(bomItemId)) {

                BomItem next = items.get(i + 1);

                Integer temp = next.getSequence();
                next.setSequence(current.getSequence());
                current.setSequence(temp);

                bomItemRepository.save(next);
                bomItemRepository.save(current);

                break;
            }
        }

        return mapToDTO(current);
    }

    // ==========================================================
    // DTO MAPPING
    // ==========================================================

    private BomItemDTO mapToDTO(BomItem bomItem) {

        return BomItemDTO.builder()
                .bomItemId(bomItem.getBomItemId())
                .recipeId(bomItem.getRecipe().getRecipeId())
                .materialId(bomItem.getMaterial().getMaterialId())
                .quantity(bomItem.getQuantity())
                .unit(bomItem.getUnit())
                .sequence(bomItem.getSequence())
                .optionalMaterial(bomItem.getOptionalMaterial())
                .remarks(bomItem.getRemarks())
                .build();
    }

    private BomItem mapToEntity(BomItemDTO dto) {

        return BomItem.builder()
                .quantity(dto.getQuantity())
                .unit(dto.getUnit())
                .sequence(dto.getSequence())
                .optionalMaterial(
                        dto.getOptionalMaterial() == null
                                ? false
                                : dto.getOptionalMaterial())
                .remarks(dto.getRemarks())
                .build();
    }

}