package com.anandh.mes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.anandh.mes.entity.BomItem;

public interface BomItemRepository extends JpaRepository<BomItem, Long> {

    List<BomItem> findByRecipeRecipeIdOrderBySequenceAsc(
            Long recipeId);

    List<BomItem> findByMaterialMaterialId(
            Long materialId);

    List<BomItem> findByOptionalMaterial(
            Boolean optionalMaterial);

    List<BomItem> findByRecipeRecipeIdAndOptionalMaterial(
            Long recipeId,
            Boolean optionalMaterial);

    Page<BomItem> findAll(
            Pageable pageable);

}