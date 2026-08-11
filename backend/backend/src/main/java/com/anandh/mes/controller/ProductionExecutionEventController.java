package com.anandh.mes.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.anandh.mes.dto.ProductionExecutionEventDTO;
import com.anandh.mes.enums.ProductionEventType;
import com.anandh.mes.service.ProductionExecutionEventService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/production-execution-events")
@RequiredArgsConstructor
public class ProductionExecutionEventController {

    private final ProductionExecutionEventService eventService;

    // ==========================================================
    // CREATE EVENT
    // ==========================================================

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductionExecutionEventDTO createEvent(
            @Valid @RequestBody
            ProductionExecutionEventDTO dto) {

        return eventService.createEvent(dto);
    }

    // ==========================================================
    // GET ALL
    // ==========================================================

    @GetMapping
    public List<ProductionExecutionEventDTO> getAllEvents() {

        return eventService.getAllEvents();
    }

    // ==========================================================
    // GET BY ID
    // ==========================================================

    @GetMapping("/{id}")
    public ProductionExecutionEventDTO getEventById(
            @PathVariable Long id) {

        return eventService.getEventById(id);
    }

    // ==========================================================
    // DELETE
    // ==========================================================

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(
            @PathVariable Long id) {

        eventService.deleteEvent(id);
    }

    // ==========================================================
    // BY WORK ORDER
    // ==========================================================

    @GetMapping("/work-order/{workOrderId}")
    public List<ProductionExecutionEventDTO>
            getByWorkOrder(
                    @PathVariable Long workOrderId) {

        return eventService
                .getByWorkOrder(workOrderId);
    }

    // ==========================================================
    // BY BATCH
    // ==========================================================

    @GetMapping("/batch/{batchId}")
    public List<ProductionExecutionEventDTO>
            getByBatch(
                    @PathVariable Long batchId) {

        return eventService
                .getByBatch(batchId);
    }

    // ==========================================================
    // BY ASSIGNMENT
    // ==========================================================

    @GetMapping("/assignment/{assignmentId}")
    public List<ProductionExecutionEventDTO>
            getByAssignment(
                    @PathVariable Long assignmentId) {

        return eventService
                .getByAssignment(assignmentId);
    }

    // ==========================================================
    // BY EVENT TYPE
    // ==========================================================

    @GetMapping("/type/{eventType}")
    public List<ProductionExecutionEventDTO>
            getByEventType(
                    @PathVariable ProductionEventType eventType) {

        return eventService
                .getByEventType(eventType);
    }

    // ==========================================================
    // BY OPERATOR
    // ==========================================================

    @GetMapping("/operator")
    public List<ProductionExecutionEventDTO>
            getByOperator(
                    @RequestParam String operatorName) {

        return eventService
                .getByOperator(operatorName);
    }

    // ==========================================================
    // DATE RANGE
    // ==========================================================

    @GetMapping("/date-range")
    public List<ProductionExecutionEventDTO>
            getByEventTimeBetween(
                    @RequestParam LocalDateTime start,
                    @RequestParam LocalDateTime end) {

        return eventService
                .getByEventTimeBetween(
                        start,
                        end);
    }

    // ==========================================================
    // WORK ORDER + EVENT TYPE
    // ==========================================================

    @GetMapping(
            "/work-order/{workOrderId}/type/{eventType}")
    public List<ProductionExecutionEventDTO>
            getByWorkOrderAndEventType(

                    @PathVariable Long workOrderId,

                    @PathVariable
                    ProductionEventType eventType) {

        return eventService
                .getByWorkOrderAndEventType(
                        workOrderId,
                        eventType);
    }

    // ==========================================================
    // WORK ORDER + DATE RANGE
    // ==========================================================

    @GetMapping(
            "/work-order/{workOrderId}/date-range")
    public List<ProductionExecutionEventDTO>
            getByWorkOrderAndEventTimeBetween(

                    @PathVariable Long workOrderId,

                    @RequestParam LocalDateTime start,

                    @RequestParam LocalDateTime end) {

        return eventService
                .getByWorkOrderAndEventTimeBetween(
                        workOrderId,
                        start,
                        end);
    }

    // ==========================================================
    // PAGINATION
    // ==========================================================

    @GetMapping("/page")
    public Page<ProductionExecutionEventDTO>
            getEventPage(

                    @RequestParam(defaultValue = "0")
                    int page,

                    @RequestParam(defaultValue = "10")
                    int size,

                    @RequestParam(
                            defaultValue = "productionExecutionEventId")
                    String sortBy) {

        return eventService
                .getEventPage(
                        page,
                        size,
                        sortBy);
    }

}