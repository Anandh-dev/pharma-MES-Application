package com.anandh.mes.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.anandh.mes.dto.BomItemDTO;
import com.anandh.mes.service.BomItemService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bom-items")
@RequiredArgsConstructor
public class BomItemController {

    private final BomItemService bomItemService;

    // ==========================================================
    // CRUD
    // ==========================================================

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BomItemDTO createBomItem(
            @Valid @RequestBody BomItemDTO bomItemDTO) {

        return bomItemService.createBomItem(bomItemDTO);
    }

    @GetMapping
    public List<BomItemDTO> getAllBomItems() {

        return bomItemService.getAllBomItems();
    }

    @GetMapping("/{id}")
    public BomItemDTO getBomItemById(
            @PathVariable Long id) {

        return bomItemService.getBomItemById(id);
    }

    @PutMapping("/{id}")
    public BomItemDTO updateBomItem(
            @PathVariable Long id,
            @Valid @RequestBody BomItemDTO bomItemDTO) {

        return bomItemService.updateBomItem(id, bomItemDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBomItem(
            @PathVariable Long id) {

        bomItemService.deleteBomItem(id);
    }

    // ==========================================================
    // SEARCH
    // ==========================================================

    @GetMapping("/recipe/{recipeId}")
    public List<BomItemDTO> getBomItemsByRecipe(
            @PathVariable Long recipeId) {

        return bomItemService.getBomItemsByRecipe(recipeId);
    }

    @GetMapping("/material/{materialId}")
    public List<BomItemDTO> getBomItemsByMaterial(
            @PathVariable Long materialId) {

        return bomItemService.getBomItemsByMaterial(materialId);
    }

    @GetMapping("/optional")
    public List<BomItemDTO> getOptionalMaterials() {

        return bomItemService.getOptionalMaterials();
    }

    @GetMapping("/mandatory/{recipeId}")
    public List<BomItemDTO> getMandatoryMaterials(
            @PathVariable Long recipeId) {

        return bomItemService.getMandatoryMaterials(recipeId);
    }

    @GetMapping("/page")
    public Page<BomItemDTO> getBomItemPage(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "bomItemId")
            String sortBy) {

        return bomItemService.getBomItemPage(
                page,
                size,
                sortBy);
    }

    // ==========================================================
    // BOM MANAGEMENT
    // ==========================================================

    @PutMapping("/{id}/optional")
    public BomItemDTO markOptional(
            @PathVariable Long id) {

        return bomItemService.markOptional(id);
    }

    @PutMapping("/{id}/mandatory")
    public BomItemDTO markMandatory(
            @PathVariable Long id) {

        return bomItemService.markMandatory(id);
    }

    @PutMapping("/{id}/move-up")
    public BomItemDTO moveMaterialUp(
            @PathVariable Long id) {

        return bomItemService.moveMaterialUp(id);
    }

    @PutMapping("/{id}/move-down")
    public BomItemDTO moveMaterialDown(
            @PathVariable Long id) {

        return bomItemService.moveMaterialDown(id);
    }

}