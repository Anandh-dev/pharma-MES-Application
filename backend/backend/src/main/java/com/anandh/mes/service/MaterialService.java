package com.anandh.mes.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.anandh.mes.dto.MaterialDTO;
import com.anandh.mes.enums.MaterialCategory;
import com.anandh.mes.enums.MaterialStatus;

public interface MaterialService {

    MaterialDTO createMaterial(MaterialDTO materialDTO);

    List<MaterialDTO> getAllMaterials();

    MaterialDTO getMaterialById(Long id);

    MaterialDTO updateMaterial(Long id, MaterialDTO materialDTO);

    void deleteMaterial(Long id);

    List<MaterialDTO> searchByName(String name);

    List<MaterialDTO> getByCategory(MaterialCategory category);

    List<MaterialDTO> getByStatus(MaterialStatus status);

    Page<MaterialDTO> getMaterialPage(
            int page,
            int size,
            String sortBy);

}