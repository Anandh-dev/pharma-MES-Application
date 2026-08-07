package com.anandh.mes.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.anandh.mes.dto.BomItemDTO;

public interface BomItemService {

    // ==========================================================
    // CRUD
    // ==========================================================

    BomItemDTO createBomItem(
            BomItemDTO bomItemDTO);

    List<BomItemDTO> getAllBomItems();

    BomItemDTO getBomItemById(
            Long id);

    BomItemDTO updateBomItem(
            Long id,
            BomItemDTO bomItemDTO);

    void deleteBomItem(
            Long id);

    // ==========================================================
    // SEARCH
    // ==========================================================

    List<BomItemDTO> getBomItemsByRecipe(
            Long recipeId);

    List<BomItemDTO> getBomItemsByMaterial(
            Long materialId);

    List<BomItemDTO> getOptionalMaterials();

    List<BomItemDTO> getMandatoryMaterials(
            Long recipeId);

    Page<BomItemDTO> getBomItemPage(
            int page,
            int size,
            String sortBy);

    // ==========================================================
    // BOM MANAGEMENT
    // ==========================================================

    BomItemDTO markOptional(
            Long bomItemId);

    BomItemDTO markMandatory(
            Long bomItemId);

    BomItemDTO moveMaterialUp(
            Long bomItemId);

    BomItemDTO moveMaterialDown(
            Long bomItemId);

}