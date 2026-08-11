package com.anandh.mes.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.anandh.mes.dto.WorkOrderAssignmentDTO;
import com.anandh.mes.enums.AssignmentStatus;
import com.anandh.mes.service.WorkOrderAssignmentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/work-order-assignments")
@RequiredArgsConstructor
public class WorkOrderAssignmentController {

    private final WorkOrderAssignmentService
            assignmentService;

    // ==========================================================
    // CREATE
    // ==========================================================

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkOrderAssignmentDTO createAssignment(
            @Valid @RequestBody WorkOrderAssignmentDTO dto) {

        return assignmentService.createAssignment(dto);
    }

    // ==========================================================
    // GET ALL
    // ==========================================================

    @GetMapping
    public List<WorkOrderAssignmentDTO> getAllAssignments() {

        return assignmentService.getAllAssignments();
    }

    // ==========================================================
    // GET BY ID
    // ==========================================================

    @GetMapping("/{id}")
    public WorkOrderAssignmentDTO getAssignmentById(
            @PathVariable Long id) {

        return assignmentService.getAssignmentById(id);
    }

    // ==========================================================
    // UPDATE
    // ==========================================================

    @PutMapping("/{id}")
    public WorkOrderAssignmentDTO updateAssignment(
            @PathVariable Long id,
            @Valid @RequestBody WorkOrderAssignmentDTO dto) {

        return assignmentService.updateAssignment(id, dto);
    }

    // ==========================================================
    // DELETE
    // ==========================================================

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAssignment(
            @PathVariable Long id) {

        assignmentService.deleteAssignment(id);
    }

    // ==========================================================
    // BY WORK ORDER
    // ==========================================================

    @GetMapping("/work-order/{workOrderId}")
    public List<WorkOrderAssignmentDTO> getByWorkOrder(
            @PathVariable Long workOrderId) {

        return assignmentService
                .getByWorkOrder(workOrderId);
    }

    // ==========================================================
    // BY OPERATOR
    // ==========================================================

    @GetMapping("/operator")
    public List<WorkOrderAssignmentDTO> getByOperator(
            @RequestParam String operatorName) {

        return assignmentService
                .getByOperator(operatorName);
    }

    // ==========================================================
    // BY WORK CENTER
    // ==========================================================

    @GetMapping("/work-center")
    public List<WorkOrderAssignmentDTO> getByWorkCenter(
            @RequestParam String workCenter) {

        return assignmentService
                .getByWorkCenter(workCenter);
    }

    // ==========================================================
    // BY OPERATION
    // ==========================================================

    @GetMapping("/operation")
    public List<WorkOrderAssignmentDTO> getByOperation(
            @RequestParam String operationName) {

        return assignmentService
                .getByOperation(operationName);
    }

    // ==========================================================
    // BY STATUS
    // ==========================================================

    @GetMapping("/status/{status}")
    public List<WorkOrderAssignmentDTO> getByStatus(
            @PathVariable AssignmentStatus status) {

        return assignmentService
                .getByStatus(status);
    }

    // ==========================================================
    // OPERATOR + STATUS
    // ==========================================================

    @GetMapping("/operator/{operatorName}/status/{status}")
    public List<WorkOrderAssignmentDTO>
            getByOperatorAndStatus(
                    @PathVariable String operatorName,
                    @PathVariable AssignmentStatus status) {

        return assignmentService
                .getByOperatorAndStatus(
                        operatorName,
                        status);
    }

    // ==========================================================
    // WORK CENTER + STATUS
    // ==========================================================

    @GetMapping("/work-center/{workCenter}/status/{status}")
    public List<WorkOrderAssignmentDTO>
            getByWorkCenterAndStatus(
                    @PathVariable String workCenter,
                    @PathVariable AssignmentStatus status) {

        return assignmentService
                .getByWorkCenterAndStatus(
                        workCenter,
                        status);
    }

    // ==========================================================
    // DATE RANGE
    // ==========================================================

    @GetMapping("/date-range")
    public List<WorkOrderAssignmentDTO>
            getByAssignedAtBetween(
                    @RequestParam LocalDateTime start,
                    @RequestParam LocalDateTime end) {

        return assignmentService
                .getByAssignedAtBetween(start, end);
    }

    // ==========================================================
    // PAGINATION
    // ==========================================================

    @GetMapping("/page")
    public Page<WorkOrderAssignmentDTO> getAssignmentPage(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "assignmentId")
            String sortBy) {

        return assignmentService
                .getAssignmentPage(
                        page,
                        size,
                        sortBy);
    }

    // ==========================================================
    // ACCEPT
    // ==========================================================

    @PutMapping("/{id}/accept")
    public WorkOrderAssignmentDTO acceptAssignment(
            @PathVariable Long id) {

        return assignmentService
                .acceptAssignment(id);
    }

    // ==========================================================
    // START
    // ==========================================================

    @PutMapping("/{id}/start")
    public WorkOrderAssignmentDTO startAssignment(
            @PathVariable Long id) {

        return assignmentService
                .startAssignment(id);
    }

    // ==========================================================
    // COMPLETE
    // ==========================================================

    @PutMapping("/{id}/complete")
    public WorkOrderAssignmentDTO completeAssignment(
            @PathVariable Long id) {

        return assignmentService
                .completeAssignment(id);
    }

    // ==========================================================
    // CANCEL
    // ==========================================================

    @PutMapping("/{id}/cancel")
    public WorkOrderAssignmentDTO cancelAssignment(
            @PathVariable Long id) {

        return assignmentService
                .cancelAssignment(id);
    }

}