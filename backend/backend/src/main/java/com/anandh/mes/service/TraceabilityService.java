package com.anandh.mes.service;

import java.util.List;

import com.anandh.mes.dto.TraceabilityNodeDTO;

public interface TraceabilityService {

    // ==========================================================
    // BACKWARD TRACEABILITY
    // ==========================================================

    /**
     * Find the batches/materials that contributed to
     * the specified batch.
     */
    List<TraceabilityNodeDTO> getBackwardTraceability(
            Long batchId);

    // ==========================================================
    // FORWARD TRACEABILITY
    // ==========================================================

    /**
     * Find the batches/products that were created from
     * the specified batch.
     */
    List<TraceabilityNodeDTO> getForwardTraceability(
            Long batchId);

    // ==========================================================
    // IMPACT ANALYSIS
    // ==========================================================

    /**
     * Find downstream batches affected by a particular
     * batch or material lot.
     */
    List<TraceabilityNodeDTO> getImpactAnalysis(
            Long batchId);

    // ==========================================================
    // RELATED BATCHES
    // ==========================================================

    /**
     * Find all directly related batches regardless of
     * traceability direction.
     */
    List<TraceabilityNodeDTO> getRelatedBatches(
            Long batchId);

}