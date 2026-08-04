package com.anandh.mes.service.impl;

import java.util.List;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anandh.mes.dto.MaterialDTO;
import com.anandh.mes.entity.Material;
import com.anandh.mes.enums.MaterialCategory;
import com.anandh.mes.enums.MaterialStatus;
import com.anandh.mes.exception.ResourceNotFoundException;
import com.anandh.mes.repository.MaterialRepository;
import com.anandh.mes.service.MaterialService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;

    @Override
    public MaterialDTO createMaterial(MaterialDTO dto) {

        if (materialRepository.existsByMaterialCode(dto.getMaterialCode())) {
            throw new RuntimeException(
                    "Material Code already exists");
        }

        Material material = mapToEntity(dto);

        Material savedMaterial = materialRepository.save(material);

        return mapToDTO(savedMaterial);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaterialDTO> getAllMaterials() {

        return materialRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MaterialDTO getMaterialById(Long id) {

        Material material = materialRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Material not found with id : " + id));

        return mapToDTO(material);
    }

    @Override
    public MaterialDTO updateMaterial(Long id,
                                      MaterialDTO dto) {

        Material material = materialRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Material not found with id : " + id));

        material.setMaterialCode(dto.getMaterialCode());
        material.setMaterialName(dto.getMaterialName());
        material.setCategory(dto.getCategory());
        material.setStatus(dto.getStatus());
        material.setUnit(dto.getUnit());
        material.setManufacturer(dto.getManufacturer());
        material.setDescription(dto.getDescription());

        return mapToDTO(materialRepository.save(material));
    }

    @Override
    public void deleteMaterial(Long id) {

        Material material = materialRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Material not found with id : " + id));

        materialRepository.delete(material);
    }

    @Override
    public List<MaterialDTO> searchByName(String name) {

        return materialRepository
                .findByMaterialNameContainingIgnoreCase(name)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<MaterialDTO> getByCategory(MaterialCategory category) {

        return materialRepository
                .findByCategory(category)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<MaterialDTO> getByStatus(MaterialStatus status) {

        return materialRepository
                .findByStatus(status)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public Page<MaterialDTO> getMaterialPage(
            int page,
            int size,
            String sortBy) {

        Pageable pageable =
                PageRequest.of(page, size, Sort.by(sortBy));

        return materialRepository
                .findAll(pageable)
                .map(this::mapToDTO);
    }

    private MaterialDTO mapToDTO(Material material) {

        return MaterialDTO.builder()
                .materialId(material.getMaterialId())
                .materialCode(material.getMaterialCode())
                .materialName(material.getMaterialName())
                .category(material.getCategory())
                .status(material.getStatus())
                .unit(material.getUnit())
                .manufacturer(material.getManufacturer())
                .description(material.getDescription())
                .build();
    }

    private Material mapToEntity(MaterialDTO dto) {

        return Material.builder()
                .materialCode(dto.getMaterialCode())
                .materialName(dto.getMaterialName())
                .category(dto.getCategory())
                .status(dto.getStatus())
                .unit(dto.getUnit())
                .manufacturer(dto.getManufacturer())
                .description(dto.getDescription())
                .build();
    }
}