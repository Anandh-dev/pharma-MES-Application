package com.anandh.mes.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.anandh.mes.entity.ProductionSchedule;
import com.anandh.mes.enums.ProductionScheduleStatus;

public interface ProductionScheduleRepository
        extends JpaRepository<ProductionSchedule, Long> {

    List<ProductionSchedule> findByStatus(
            ProductionScheduleStatus status);

    List<ProductionSchedule> findByProductNameContainingIgnoreCase(
            String productName);

    List<ProductionSchedule> findByPriority(
            Integer priority);

    List<ProductionSchedule> findByPlannedStartBetween(
            LocalDateTime start,
            LocalDateTime end);

    List<ProductionSchedule>
    findByProductNameContainingIgnoreCaseAndStatus(
            String productName,
            ProductionScheduleStatus status);

    List<ProductionSchedule> findByPriorityAndStatus(
            Integer priority,
            ProductionScheduleStatus status);

    // ADD THIS
    Optional<ProductionSchedule> findByScheduleNumber(
            String scheduleNumber);

    Page<ProductionSchedule> findAll(
            Pageable pageable);
}