package com.anandh.mes.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.anandh.mes.dto.TraceabilityNodeDTO;
import com.anandh.mes.service.TraceabilityService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/traceability")
@RequiredArgsConstructor
public class TraceabilityController {

    private final TraceabilityService traceabilityService;

    // ==========================================================
    // BACKWARD TRACEABILITY
    // ==========================================================

    @GetMapping("/batch/{batchId}/backward")
    public List<TraceabilityNodeDTO> getBackwardTraceability(
            @PathVariable Long batchId) {

        return traceabilityService
                .getBackwardTraceability(batchId);
    }

    // ==========================================================
    // FORWARD TRACEABILITY
    // ==========================================================

    @GetMapping("/batch/{batchId}/forward")
    public List<TraceabilityNodeDTO> getForwardTraceability(
            @PathVariable Long batchId) {

        return traceabilityService
                .getForwardTraceability(batchId);
    }

    // ==========================================================
    // IMPACT ANALYSIS
    // ==========================================================

    @GetMapping("/batch/{batchId}/impact")
    public List<TraceabilityNodeDTO> getImpactAnalysis(
            @PathVariable Long batchId) {

        return traceabilityService
                .getImpactAnalysis(batchId);
    }

    // ==========================================================
    // RELATED BATCHES
    // ==========================================================

    @GetMapping("/batch/{batchId}/related")
    public List<TraceabilityNodeDTO> getRelatedBatches(
            @PathVariable Long batchId) {

        return traceabilityService
                .getRelatedBatches(batchId);
    }

}