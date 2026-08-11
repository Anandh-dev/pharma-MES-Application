package com.anandh.mes.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.anandh.mes.dto.ProductionScheduleDTO;
import com.anandh.mes.enums.ProductionScheduleStatus;
import com.anandh.mes.service.ProductionScheduleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/production-schedules")
@RequiredArgsConstructor
public class ProductionScheduleController {

    private final ProductionScheduleService
            productionScheduleService;

    // ==========================================================
    // CREATE
    // ==========================================================

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductionScheduleDTO createProductionSchedule(
            @Valid @RequestBody ProductionScheduleDTO dto) {

        return productionScheduleService
                .createProductionSchedule(dto);
    }

    // ==========================================================
    // GET ALL
    // ==========================================================

    @GetMapping
    public List<ProductionScheduleDTO>
            getAllProductionSchedules() {

        return productionScheduleService
                .getAllProductionSchedules();
    }

    // ==========================================================
    // GET BY ID
    // ==========================================================

    @GetMapping("/{id}")
    public ProductionScheduleDTO
            getProductionScheduleById(
                    @PathVariable Long id) {

        return productionScheduleService
                .getProductionScheduleById(id);
    }

    // ==========================================================
    // UPDATE
    // ==========================================================

    @PutMapping("/{id}")
    public ProductionScheduleDTO
            updateProductionSchedule(
                    @PathVariable Long id,
                    @Valid @RequestBody
                    ProductionScheduleDTO dto) {

        return productionScheduleService
                .updateProductionSchedule(id, dto);
    }

    // ==========================================================
    // DELETE
    // ==========================================================

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductionSchedule(
            @PathVariable Long id) {

        productionScheduleService
                .deleteProductionSchedule(id);
    }

    // ==========================================================
    // SEARCH BY STATUS
    // ==========================================================

    @GetMapping("/status/{status}")
    public List<ProductionScheduleDTO> getByStatus(
            @PathVariable ProductionScheduleStatus status) {

        return productionScheduleService
                .getByStatus(status);
    }

    // ==========================================================
    // SEARCH BY PRODUCT
    // ==========================================================

    @GetMapping("/product")
    public List<ProductionScheduleDTO> getByProduct(
            @RequestParam String productName) {

        return productionScheduleService
                .getByProduct(productName);
    }

    // ==========================================================
    // SEARCH BY PRIORITY
    // ==========================================================

    @GetMapping("/priority/{priority}")
    public List<ProductionScheduleDTO> getByPriority(
            @PathVariable Integer priority) {

        return productionScheduleService
                .getByPriority(priority);
    }

    // ==========================================================
    // SEARCH BY DATE RANGE
    // ==========================================================

    @GetMapping("/date-range")
    public List<ProductionScheduleDTO>
            getByPlannedStartBetween(

            @RequestParam LocalDateTime start,

            @RequestParam LocalDateTime end) {

        return productionScheduleService
                .getByPlannedStartBetween(
                        start,
                        end);
    }

    // ==========================================================
    // PRODUCT + STATUS
    // ==========================================================

    @GetMapping("/product/{productName}/status/{status}")
    public List<ProductionScheduleDTO>
            getByProductAndStatus(

            @PathVariable String productName,

            @PathVariable ProductionScheduleStatus status) {

        return productionScheduleService
                .getByProductAndStatus(
                        productName,
                        status);
    }

    // ==========================================================
    // PRIORITY + STATUS
    // ==========================================================

    @GetMapping("/priority/{priority}/status/{status}")
    public List<ProductionScheduleDTO>
            getByPriorityAndStatus(

            @PathVariable Integer priority,

            @PathVariable ProductionScheduleStatus status) {

        return productionScheduleService
                .getByPriorityAndStatus(
                        priority,
                        status);
    }

    // ==========================================================
    // PAGINATION
    // ==========================================================

    @GetMapping("/page")
    public Page<ProductionScheduleDTO>
            getProductionSchedulePage(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "productionScheduleId")
            String sortBy) {

        return productionScheduleService
                .getProductionSchedulePage(
                        page,
                        size,
                        sortBy);
    }

}