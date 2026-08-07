package com.anandh.mes.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.anandh.mes.dto.ProductionOrderDTO;
import com.anandh.mes.enums.ProductionStatus;
import com.anandh.mes.service.ProductionOrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/production-orders")
@RequiredArgsConstructor
public class ProductionOrderController {

    private final ProductionOrderService productionOrderService;

    // ==========================================================
    // CRUD
    // ==========================================================

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductionOrderDTO createProductionOrder(
            @Valid @RequestBody ProductionOrderDTO productionOrderDTO) {

        return productionOrderService.createProductionOrder(productionOrderDTO);
    }

    @GetMapping
    public List<ProductionOrderDTO> getAllProductionOrders() {

        return productionOrderService.getAllProductionOrders();
    }

    @GetMapping("/{id}")
    public ProductionOrderDTO getProductionOrderById(
            @PathVariable Long id) {

        return productionOrderService.getProductionOrderById(id);
    }

    @PutMapping("/{id}")
    public ProductionOrderDTO updateProductionOrder(
            @PathVariable Long id,
            @Valid @RequestBody ProductionOrderDTO productionOrderDTO) {

        return productionOrderService.updateProductionOrder(id, productionOrderDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductionOrder(
            @PathVariable Long id) {

        productionOrderService.deleteProductionOrder(id);
    }

    // ==========================================================
    // SEARCH
    // ==========================================================

    @GetMapping("/order/{orderNumber}")
    public ProductionOrderDTO getByOrderNumber(
            @PathVariable String orderNumber) {

        return productionOrderService.getByOrderNumber(orderNumber);
    }

    @GetMapping("/batch/{batchNumber}")
    public ProductionOrderDTO getByBatchNumber(
            @PathVariable String batchNumber) {

        return productionOrderService.getByBatchNumber(batchNumber);
    }

    @GetMapping("/status/{status}")
    public List<ProductionOrderDTO> getByStatus(
            @PathVariable ProductionStatus status) {

        return productionOrderService.getByStatus(status);
    }

    @GetMapping("/material/{materialId}")
    public List<ProductionOrderDTO> getByMaterial(
            @PathVariable Long materialId) {

        return productionOrderService.getByMaterial(materialId);
    }

    @GetMapping("/planned-date/{plannedDate}")
    public List<ProductionOrderDTO> getByPlannedDate(

            @PathVariable
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate plannedDate) {

        return productionOrderService.getByPlannedStartDate(plannedDate);
    }

    @GetMapping("/date-range")
    public List<ProductionOrderDTO> getByDateRange(

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate) {

        return productionOrderService.getByDateRange(startDate, endDate);
    }

    @GetMapping("/page")
    public Page<ProductionOrderDTO> getProductionOrderPage(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "productionOrderId")
            String sortBy) {

        return productionOrderService
                .getProductionOrderPage(page, size, sortBy);
    }

    // ==========================================================
    // PRODUCTION WORKFLOW
    // ==========================================================

    @PutMapping("/{id}/release")
    public ProductionOrderDTO releaseProductionOrder(
            @PathVariable Long id) {

        return productionOrderService.releaseProductionOrder(id);
    }

    @PutMapping("/{id}/reserve-materials")
    public ProductionOrderDTO reserveMaterials(
            @PathVariable Long id) {

        return productionOrderService.reserveMaterials(id);
    }

    @PutMapping("/{id}/start")
    public ProductionOrderDTO startProduction(
            @PathVariable Long id) {

        return productionOrderService.startProduction(id);
    }

    @PutMapping("/{id}/complete")
    public ProductionOrderDTO completeProduction(

            @PathVariable Long id,

            @RequestParam Double producedQuantity) {

        return productionOrderService
                .completeProduction(id, producedQuantity);
    }

    @PutMapping("/{id}/close")
    public ProductionOrderDTO closeProductionOrder(
            @PathVariable Long id) {

        return productionOrderService.closeProductionOrder(id);
    }

    @PutMapping("/{id}/cancel")
    public ProductionOrderDTO cancelProductionOrder(
            @PathVariable Long id) {

        return productionOrderService.cancelProductionOrder(id);
    }

}