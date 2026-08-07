package com.anandh.mes.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.anandh.mes.dto.ProcessParameterLogDTO;
import com.anandh.mes.service.ProcessParameterLogService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/process-parameter-logs")
@RequiredArgsConstructor
public class ProcessParameterLogController {

    private final ProcessParameterLogService processParameterLogService;

    // ==========================================================
    // CRUD
    // ==========================================================

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProcessParameterLogDTO createProcessParameterLog(
            @Valid @RequestBody ProcessParameterLogDTO processParameterLogDTO) {

        return processParameterLogService.createProcessParameterLog(
                processParameterLogDTO);
    }

    @GetMapping
    public List<ProcessParameterLogDTO> getAllProcessParameterLogs() {

        return processParameterLogService.getAllProcessParameterLogs();
    }

    @GetMapping("/{id}")
    public ProcessParameterLogDTO getProcessParameterLogById(
            @PathVariable Long id) {

        return processParameterLogService.getProcessParameterLogById(id);
    }

    @PutMapping("/{id}")
    public ProcessParameterLogDTO updateProcessParameterLog(
            @PathVariable Long id,
            @Valid @RequestBody ProcessParameterLogDTO processParameterLogDTO) {

        return processParameterLogService.updateProcessParameterLog(
                id,
                processParameterLogDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProcessParameterLog(
            @PathVariable Long id) {

        processParameterLogService.deleteProcessParameterLog(id);
    }

    // ==========================================================
    // SEARCH
    // ==========================================================

    @GetMapping("/batch/{batchId}")
    public List<ProcessParameterLogDTO> getByBatch(
            @PathVariable Long batchId) {

        return processParameterLogService.getByBatch(batchId);
    }

    @GetMapping("/parameter")
    public List<ProcessParameterLogDTO> getByParameterName(
            @RequestParam String parameterName) {

        return processParameterLogService.getByParameterName(parameterName);
    }

    @GetMapping("/recorded-by")
    public List<ProcessParameterLogDTO> getByRecordedBy(
            @RequestParam String recordedBy) {

        return processParameterLogService.getByRecordedBy(recordedBy);
    }

    @GetMapping("/batch/{batchId}/parameter")
    public List<ProcessParameterLogDTO> getByBatchAndParameter(
            @PathVariable Long batchId,
            @RequestParam String parameterName) {

        return processParameterLogService.getByBatchAndParameter(
                batchId,
                parameterName);
    }

    @GetMapping("/page")
    public Page<ProcessParameterLogDTO> getProcessParameterLogPage(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "processParameterLogId")
            String sortBy) {

        return processParameterLogService.getProcessParameterLogPage(
                page,
                size,
                sortBy);
    }

    // ==========================================================
    // PROCESS MONITORING
    // ==========================================================

    @PostMapping("/record")
    public ProcessParameterLogDTO recordParameter(
            @Valid @RequestBody ProcessParameterLogDTO processParameterLogDTO) {

        return processParameterLogService.recordParameter(
                processParameterLogDTO);
    }

}