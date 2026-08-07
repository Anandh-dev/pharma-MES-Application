package com.anandh.mes.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;

import com.anandh.mes.dto.ProductionOrderDTO;
import com.anandh.mes.enums.ProductionStatus;

public interface ProductionOrderService {

    // CRUD

    ProductionOrderDTO createProductionOrder(
            ProductionOrderDTO productionOrderDTO);

    List<ProductionOrderDTO> getAllProductionOrders();

    ProductionOrderDTO getProductionOrderById(Long id);

    ProductionOrderDTO updateProductionOrder(
            Long id,
            ProductionOrderDTO productionOrderDTO);

    void deleteProductionOrder(Long id);

    // Search

    ProductionOrderDTO getByOrderNumber(String orderNumber);

    ProductionOrderDTO getByBatchNumber(String batchNumber);

    List<ProductionOrderDTO> getByStatus(
            ProductionStatus status);

    List<ProductionOrderDTO> getByMaterial(
            Long materialId);

    List<ProductionOrderDTO> getByPlannedStartDate(
            LocalDate plannedStartDate);

    List<ProductionOrderDTO> getByDateRange(
            LocalDate startDate,
            LocalDate endDate);

    Page<ProductionOrderDTO> getProductionOrderPage(
            int page,
            int size,
            String sortBy);

    // Workflow Operations

    ProductionOrderDTO releaseProductionOrder(Long id);

    ProductionOrderDTO reserveMaterials(Long id);

    ProductionOrderDTO startProduction(Long id);

    ProductionOrderDTO completeProduction(
            Long id,
            Double producedQuantity);

    ProductionOrderDTO closeProductionOrder(Long id);

    ProductionOrderDTO cancelProductionOrder(Long id);

}