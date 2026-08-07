package com.anandh.mes.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anandh.mes.dto.ProductionOrderDTO;
import com.anandh.mes.entity.Material;
import com.anandh.mes.entity.ProductionOrder;
import com.anandh.mes.enums.ProductionStatus;
import com.anandh.mes.exception.ResourceNotFoundException;
import com.anandh.mes.repository.MaterialRepository;
import com.anandh.mes.repository.ProductionOrderRepository;
import com.anandh.mes.service.ProductionOrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductionOrderServiceImpl implements ProductionOrderService {

    private final ProductionOrderRepository productionOrderRepository;

    private final MaterialRepository materialRepository;

    // ==========================================================
    // CREATE
    // ==========================================================

    @Override
    public ProductionOrderDTO createProductionOrder(
            ProductionOrderDTO dto) {

        Material material = materialRepository.findById(dto.getMaterialId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Material not found"));

        ProductionOrder productionOrder = mapToEntity(dto);

        productionOrder.setMaterial(material);
        productionOrder.setStatus(ProductionStatus.CREATED);

        ProductionOrder saved =
                productionOrderRepository.save(productionOrder);

        return mapToDTO(saved);
    }

    // ==========================================================
    // READ
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<ProductionOrderDTO> getAllProductionOrders() {

        return productionOrderRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductionOrderDTO getProductionOrderById(Long id) {

        ProductionOrder productionOrder =
                productionOrderRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Production Order not found"));

        return mapToDTO(productionOrder);
    }

    // ==========================================================
    // UPDATE
    // ==========================================================

    @Override
    public ProductionOrderDTO updateProductionOrder(
            Long id,
            ProductionOrderDTO dto) {

        ProductionOrder productionOrder =
                productionOrderRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Production Order not found"));

        Material material =
                materialRepository.findById(dto.getMaterialId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Material not found"));

        productionOrder.setOrderNumber(dto.getOrderNumber());
        productionOrder.setBatchNumber(dto.getBatchNumber());
        productionOrder.setMaterial(material);
        productionOrder.setPlannedQuantity(dto.getPlannedQuantity());
        productionOrder.setProducedQuantity(dto.getProducedQuantity());
        productionOrder.setUnit(dto.getUnit());
        productionOrder.setPlannedStartDate(dto.getPlannedStartDate());
        productionOrder.setPlannedEndDate(dto.getPlannedEndDate());
        productionOrder.setRemarks(dto.getRemarks());

        ProductionOrder updated =
                productionOrderRepository.save(productionOrder);

        return mapToDTO(updated);
    }

    // ==========================================================
    // DELETE
    // ==========================================================

    @Override
    public void deleteProductionOrder(Long id) {

        ProductionOrder productionOrder =
                productionOrderRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Production Order not found"));

        productionOrderRepository.delete(productionOrder);
    }

    // ==========================================================
    // SEARCH
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public ProductionOrderDTO getByOrderNumber(
            String orderNumber) {

        return mapToDTO(
                productionOrderRepository.findByOrderNumber(orderNumber)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Production Order not found")));
    }

    @Override
    @Transactional(readOnly = true)
    public ProductionOrderDTO getByBatchNumber(
            String batchNumber) {

        return mapToDTO(
                productionOrderRepository.findByBatchNumber(batchNumber)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Batch not found")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductionOrderDTO> getByStatus(
            ProductionStatus status) {

        return productionOrderRepository.findByStatus(status)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductionOrderDTO> getByMaterial(
            Long materialId) {

        return productionOrderRepository
                .findByMaterialMaterialId(materialId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductionOrderDTO> getByPlannedStartDate(
            java.time.LocalDate plannedStartDate) {

        return productionOrderRepository
                .findByPlannedStartDate(plannedStartDate)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductionOrderDTO> getByDateRange(
            java.time.LocalDate startDate,
            java.time.LocalDate endDate) {

        return productionOrderRepository
                .findByPlannedStartDateBetween(startDate, endDate)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // PAGINATION
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public Page<ProductionOrderDTO> getProductionOrderPage(
            int page,
            int size,
            String sortBy) {

        Pageable pageable =
                PageRequest.of(page, size, Sort.by(sortBy));

        return productionOrderRepository
                .findAll(pageable)
                .map(this::mapToDTO);
    }

    // ==========================================================
    // WORKFLOW
    // ==========================================================

    @Override
    public ProductionOrderDTO releaseProductionOrder(
            Long id) {

        ProductionOrder productionOrder =
                productionOrderRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Production Order not found"));

        productionOrder.setStatus(
                ProductionStatus.RELEASED);

        return mapToDTO(
                productionOrderRepository.save(productionOrder));
    }

    @Override
    public ProductionOrderDTO reserveMaterials(
            Long id) {

        ProductionOrder productionOrder =
                productionOrderRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Production Order not found"));

        productionOrder.setStatus(
                ProductionStatus.MATERIAL_RESERVED);

        return mapToDTO(
                productionOrderRepository.save(productionOrder));
    }

    @Override
    public ProductionOrderDTO startProduction(
            Long id) {

        ProductionOrder productionOrder =
                productionOrderRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Production Order not found"));

        productionOrder.setStatus(
                ProductionStatus.IN_PROGRESS);

        productionOrder.setActualStartTime(
                LocalDateTime.now());

        return mapToDTO(
                productionOrderRepository.save(productionOrder));
    }
    @Override
    public ProductionOrderDTO completeProduction(
            Long id,
            Double producedQuantity) {

        ProductionOrder productionOrder =
                productionOrderRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Production Order not found"));

        productionOrder.setStatus(
                ProductionStatus.COMPLETED);

        productionOrder.setProducedQuantity(producedQuantity);

        productionOrder.setActualEndTime(
                LocalDateTime.now());

        return mapToDTO(
                productionOrderRepository.save(productionOrder));
    }

    @Override
    public ProductionOrderDTO closeProductionOrder(
            Long id) {

        ProductionOrder productionOrder =
                productionOrderRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Production Order not found"));

        productionOrder.setStatus(
                ProductionStatus.CLOSED);

        return mapToDTO(
                productionOrderRepository.save(productionOrder));
    }

    @Override
    public ProductionOrderDTO cancelProductionOrder(
            Long id) {

        ProductionOrder productionOrder =
                productionOrderRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Production Order not found"));

        productionOrder.setStatus(
                ProductionStatus.CANCELLED);

        return mapToDTO(
                productionOrderRepository.save(productionOrder));
    }

    // ==========================================================
    // DTO MAPPING
    // ==========================================================

    private ProductionOrderDTO mapToDTO(
            ProductionOrder productionOrder) {

        return ProductionOrderDTO.builder()
                .productionOrderId(productionOrder.getProductionOrderId())
                .orderNumber(productionOrder.getOrderNumber())
                .batchNumber(productionOrder.getBatchNumber())
                .materialId(productionOrder.getMaterial().getMaterialId())
                .plannedQuantity(productionOrder.getPlannedQuantity())
                .producedQuantity(productionOrder.getProducedQuantity())
                .unit(productionOrder.getUnit())
                .status(productionOrder.getStatus())
                .plannedStartDate(productionOrder.getPlannedStartDate())
                .plannedEndDate(productionOrder.getPlannedEndDate())
                .actualStartTime(productionOrder.getActualStartTime())
                .actualEndTime(productionOrder.getActualEndTime())
                .remarks(productionOrder.getRemarks())
                .build();
    }

    private ProductionOrder mapToEntity(
            ProductionOrderDTO dto) {

        return ProductionOrder.builder()
                .orderNumber(dto.getOrderNumber())
                .batchNumber(dto.getBatchNumber())
                .plannedQuantity(dto.getPlannedQuantity())
                .producedQuantity(
                        dto.getProducedQuantity() == null
                                ? 0.0
                                : dto.getProducedQuantity())
                .unit(dto.getUnit())
                .status(dto.getStatus())
                .plannedStartDate(dto.getPlannedStartDate())
                .plannedEndDate(dto.getPlannedEndDate())
                .actualStartTime(dto.getActualStartTime())
                .actualEndTime(dto.getActualEndTime())
                .remarks(dto.getRemarks())
                .build();
    }

}