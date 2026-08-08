package com.anandh.mes.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anandh.mes.dto.DashboardSummaryDTO;
import com.anandh.mes.service.DashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    // ==========================================================
    // DASHBOARD SUMMARY
    // ==========================================================

    @GetMapping("/summary")
    public DashboardSummaryDTO getDashboardSummary() {

        return dashboardService.getDashboardSummary();
    }

}