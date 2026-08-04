package com.anandh.mes.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.anandh.mes.dto.MaterialDTO;
import com.anandh.mes.enums.MaterialCategory;
import com.anandh.mes.enums.MaterialStatus;
import com.anandh.mes.service.MaterialService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/materials")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MaterialDTO createMaterial(
            @Valid @RequestBody MaterialDTO materialDTO) {

        return materialService.createMaterial(materialDTO);
    }

    @GetMapping
    public List<MaterialDTO> getAllMaterials() {

        return materialService.getAllMaterials();
    }

    @GetMapping("/{id}")
    public MaterialDTO getMaterialById(
            @PathVariable Long id) {

        return materialService.getMaterialById(id);
    }

    @PutMapping("/{id}")
    public MaterialDTO updateMaterial(
            @PathVariable Long id,
            @Valid @RequestBody MaterialDTO materialDTO) {

        return materialService.updateMaterial(id, materialDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMaterial(
            @PathVariable Long id) {

        materialService.deleteMaterial(id);
    }

    @GetMapping("/search")
    public List<MaterialDTO> searchMaterial(
            @RequestParam String name) {

        return materialService.searchByName(name);
    }

    @GetMapping("/category/{category}")
    public List<MaterialDTO> getByCategory(
            @PathVariable MaterialCategory category) {

        return materialService.getByCategory(category);
    }

    @GetMapping("/status/{status}")
    public List<MaterialDTO> getByStatus(
            @PathVariable MaterialStatus status) {

        return materialService.getByStatus(status);
    }

    @GetMapping("/page")
    public Page<MaterialDTO> getMaterialPage(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size,

            @RequestParam(defaultValue = "materialId")
            String sortBy) {

        return materialService.getMaterialPage(
                page,
                size,
                sortBy);
    }

}