package com.anandh.mes.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.anandh.mes.dto.BatchEventLogDTO;
import com.anandh.mes.enums.BatchEventType;
import com.anandh.mes.service.BatchEventLogService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/batch-event-logs")
@RequiredArgsConstructor
public class BatchEventLogController {

    private final BatchEventLogService batchEventLogService;

    // ==========================================================
    // CRUD
    // ==========================================================

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BatchEventLogDTO createBatchEventLog(
            @Valid @RequestBody BatchEventLogDTO batchEventLogDTO) {

        return batchEventLogService.createBatchEventLog(
                batchEventLogDTO);
    }

    @GetMapping
    public List<BatchEventLogDTO> getAllBatchEventLogs() {

        return batchEventLogService.getAllBatchEventLogs();
    }

    @GetMapping("/{id}")
    public BatchEventLogDTO getBatchEventLogById(
            @PathVariable Long id) {

        return batchEventLogService.getBatchEventLogById(id);
    }

    @PutMapping("/{id}")
    public BatchEventLogDTO updateBatchEventLog(
            @PathVariable Long id,
            @Valid @RequestBody BatchEventLogDTO batchEventLogDTO) {

        return batchEventLogService.updateBatchEventLog(
                id,
                batchEventLogDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBatchEventLog(
            @PathVariable Long id) {

        batchEventLogService.deleteBatchEventLog(id);
    }

    // ==========================================================
    // SEARCH
    // ==========================================================

    @GetMapping("/batch/{batchId}")
    public List<BatchEventLogDTO> getByBatch(
            @PathVariable Long batchId) {

        return batchEventLogService.getByBatch(batchId);
    }

    @GetMapping("/event-type/{eventType}")
    public List<BatchEventLogDTO> getByEventType(
            @PathVariable BatchEventType eventType) {

        return batchEventLogService.getByEventType(eventType);
    }

    @GetMapping("/operator")
    public List<BatchEventLogDTO> getByOperator(
            @RequestParam String operatorName) {

        return batchEventLogService.getByOperator(operatorName);
    }

    @GetMapping("/batch/{batchId}/event-type/{eventType}")
    public List<BatchEventLogDTO> getByBatchAndEventType(
            @PathVariable Long batchId,
            @PathVariable BatchEventType eventType) {

        return batchEventLogService.getByBatchAndEventType(
                batchId,
                eventType);
    }

    @GetMapping("/page")
    public Page<BatchEventLogDTO> getBatchEventLogPage(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "batchEventLogId")
            String sortBy) {

        return batchEventLogService.getBatchEventLogPage(
                page,
                size,
                sortBy);
    }

    // ==========================================================
    // ELECTRONIC BATCH RECORD
    // ==========================================================

    @PostMapping("/record")
    public BatchEventLogDTO recordEvent(
            @Valid @RequestBody BatchEventLogDTO batchEventLogDTO) {

        return batchEventLogService.recordEvent(
                batchEventLogDTO);
    }

    @GetMapping("/ebr/{batchId}")
    public List<BatchEventLogDTO> getElectronicBatchRecord(
            @PathVariable Long batchId) {

        return batchEventLogService.getElectronicBatchRecord(batchId);
    }

}