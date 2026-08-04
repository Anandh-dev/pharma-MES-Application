package com.anandh.mes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.anandh.mes.entity.Material;
import com.anandh.mes.enums.MaterialCategory;
import com.anandh.mes.enums.MaterialStatus;

public interface MaterialRepository extends JpaRepository<Material, Long> {

    Optional<Material> findByMaterialCode(String materialCode);

    boolean existsByMaterialCode(String materialCode);

    List<Material> findByMaterialNameContainingIgnoreCase(String materialName);

    List<Material> findByCategory(MaterialCategory category);

    List<Material> findByStatus(MaterialStatus status);

    Page<Material> findAll(Pageable pageable);

}