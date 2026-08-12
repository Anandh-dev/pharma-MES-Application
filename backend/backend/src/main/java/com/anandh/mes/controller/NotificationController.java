package com.anandh.mes.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.anandh.mes.dto.NotificationDTO;
import com.anandh.mes.service.NotificationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // ==========================================================
    // CRUD
    // ==========================================================

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NotificationDTO createNotification(
            @Valid @RequestBody NotificationDTO dto) {

        return notificationService.createNotification(dto);
    }

    @GetMapping
    public List<NotificationDTO> getAllNotifications() {

        return notificationService.getAllNotifications();
    }

    @GetMapping("/{id}")
    public NotificationDTO getNotificationById(
            @PathVariable Long id) {

        return notificationService.getNotificationById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNotification(
            @PathVariable Long id) {

        notificationService.deleteNotification(id);
    }

    // ==========================================================
    // USER NOTIFICATIONS
    // ==========================================================

    @GetMapping("/recipient/{recipient}")
    public List<NotificationDTO> getNotificationsByRecipient(
            @PathVariable String recipient) {

        return notificationService
                .getNotificationsByRecipient(recipient);
    }

    @GetMapping("/recipient/{recipient}/unread")
    public List<NotificationDTO> getUnreadNotifications(
            @PathVariable String recipient) {

        return notificationService
                .getUnreadNotifications(recipient);
    }

    @GetMapping("/recipient/{recipient}/count")
    public long countUnreadNotifications(
            @PathVariable String recipient) {

        return notificationService
                .countUnreadNotifications(recipient);
    }

    // ==========================================================
    // WORKFLOW
    // ==========================================================

    @PutMapping("/{id}/read")
    public NotificationDTO markAsRead(
            @PathVariable Long id) {

        return notificationService.markAsRead(id);
    }

    @PutMapping("/recipient/{recipient}/read-all")
    public NotificationDTO markAllAsRead(
            @PathVariable String recipient) {

        return notificationService
                .markAllAsRead(recipient);
    }
}