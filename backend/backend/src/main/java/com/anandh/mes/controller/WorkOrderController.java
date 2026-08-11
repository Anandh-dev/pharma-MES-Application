package com.anandh.mes.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.anandh.mes.dto.WorkOrderDTO;
import com.anandh.mes.enums.WorkOrderStatus;
import com.anandh.mes.service.WorkOrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/work-orders")
@RequiredArgsConstructor
public class WorkOrderController {

    private final WorkOrderService workOrderService;

    // ==========================================================
    // CREATE
    // ==========================================================

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkOrderDTO createWorkOrder(
            @Valid @RequestBody WorkOrderDTO dto) {

        return workOrderService.createWorkOrder(dto);
    }

    // ==========================================================
    // GET ALL
    // ==========================================================

    @GetMapping
    public List<WorkOrderDTO> getAllWorkOrders() {

        return workOrderService.getAllWorkOrders();
    }

    // ==========================================================
    // GET BY ID
    // ==========================================================

    @GetMapping("/{id}")
    public WorkOrderDTO getWorkOrderById(
            @PathVariable Long id) {

        return workOrderService.getWorkOrderById(id);
    }

    // ==========================================================
    // UPDATE
    // ==========================================================

    @PutMapping("/{id}")
    public WorkOrderDTO updateWorkOrder(
            @PathVariable Long id,
            @Valid @RequestBody WorkOrderDTO dto) {

        return workOrderService.updateWorkOrder(id, dto);
    }

    // ==========================================================
    // DELETE
    // ==========================================================

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWorkOrder(
            @PathVariable Long id) {

        workOrderService.deleteWorkOrder(id);
    }

    // ==========================================================
    // STATUS
    // ==========================================================

    @GetMapping("/status/{status}")
    public List<WorkOrderDTO> getByStatus(
            @PathVariable WorkOrderStatus status) {

        return workOrderService.getByStatus(status);
    }

    // ==========================================================
    // PRODUCTION SCHEDULE
    // ==========================================================

    @GetMapping("/schedule/{productionScheduleId}")
    public List<WorkOrderDTO> getByProductionSchedule(
            @PathVariable Long productionScheduleId) {

        return workOrderService
                .getByProductionSchedule(
                        productionScheduleId);
    }

    // ==========================================================
    // BATCH
    // ==========================================================

    @GetMapping("/batch/{batchId}")
    public List<WorkOrderDTO> getByBatch(
            @PathVariable Long batchId) {

        return workOrderService.getByBatch(batchId);
    }

    // ==========================================================
    // PRODUCT
    // ==========================================================

    @GetMapping("/product")
    public List<WorkOrderDTO> getByProduct(
            @RequestParam String productName) {

        return workOrderService.getByProduct(productName);
    }

    // ==========================================================
    // PRIORITY
    // ==========================================================

    @GetMapping("/priority/{priority}")
    public List<WorkOrderDTO> getByPriority(
            @PathVariable Integer priority) {

        return workOrderService.getByPriority(priority);
    }

    // ==========================================================
    // DATE RANGE
    // ==========================================================

    @GetMapping("/date-range")
    public List<WorkOrderDTO>
            getByPlannedStartBetween(

            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {

        return workOrderService
                .getByPlannedStartBetween(
                        start,
                        end);
    }

    // ==========================================================
    // PRODUCT + STATUS
    // ==========================================================

    @GetMapping("/product/{productName}/status/{status}")
    public List<WorkOrderDTO> getByProductAndStatus(
            @PathVariable String productName,
            @PathVariable WorkOrderStatus status) {

        return workOrderService
                .getByProductAndStatus(
                        productName,
                        status);
    }

    // ==========================================================
    // PRIORITY + STATUS
    // ==========================================================

    @GetMapping("/priority/{priority}/status/{status}")
    public List<WorkOrderDTO> getByPriorityAndStatus(
            @PathVariable Integer priority,
            @PathVariable WorkOrderStatus status) {

        return workOrderService
                .getByPriorityAndStatus(
                        priority,
                        status);
    }

    // ==========================================================
    // PAGINATION
    // ==========================================================

    @GetMapping("/page")
    public Page<WorkOrderDTO> getWorkOrderPage(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "workOrderId")
            String sortBy) {

        return workOrderService
                .getWorkOrderPage(
                        page,
                        size,
                        sortBy);
    }

    // ==========================================================
    // RELEASE
    // ==========================================================

    @PutMapping("/{id}/release")
    public WorkOrderDTO releaseWorkOrder(
            @PathVariable Long id) {

        return workOrderService
                .releaseWorkOrder(id);
    }

    // ==========================================================
    // START
    // ==========================================================

    @PutMapping("/{id}/start")
    public WorkOrderDTO startWorkOrder(
            @PathVariable Long id) {

        return workOrderService
                .startWorkOrder(id);
    }

    // ==========================================================
    // HOLD
    // ==========================================================

    @PutMapping("/{id}/hold")
    public WorkOrderDTO holdWorkOrder(
            @PathVariable Long id) {

        return workOrderService
                .holdWorkOrder(id);
    }

    // ==========================================================
    // RESUME
    // ==========================================================

    @PutMapping("/{id}/resume")
    public WorkOrderDTO resumeWorkOrder(
            @PathVariable Long id) {

        return workOrderService
                .resumeWorkOrder(id);
    }

    // ==========================================================
    // COMPLETE
    // ==========================================================

    @PutMapping("/{id}/complete")
    public WorkOrderDTO completeWorkOrder(
            @PathVariable Long id) {

        return workOrderService
                .completeWorkOrder(id);
    }

    // ==========================================================
    // CANCEL
    // ==========================================================

    @PutMapping("/{id}/cancel")
    public WorkOrderDTO cancelWorkOrder(
            @PathVariable Long id) {

        return workOrderService
                .cancelWorkOrder(id);
    }

    // ==========================================================
    // ASSIGN BATCH
    // ==========================================================

    @PutMapping("/{workOrderId}/batch/{batchId}")
    public WorkOrderDTO assignBatch(
            @PathVariable Long workOrderId,
            @PathVariable Long batchId) {

        return workOrderService
                .assignBatch(
                        workOrderId,
                        batchId);
    }

}