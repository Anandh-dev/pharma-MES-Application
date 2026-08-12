package com.anandh.mes.service;

import java.util.List;

import com.anandh.mes.dto.NotificationDTO;

public interface NotificationService {

    // ==========================================================
    // CRUD
    // ==========================================================

    NotificationDTO createNotification(
            NotificationDTO dto);

    NotificationDTO getNotificationById(
            Long id);

    List<NotificationDTO> getAllNotifications();

    void deleteNotification(
            Long id);

    // ==========================================================
    // USER NOTIFICATIONS
    // ==========================================================

    List<NotificationDTO> getNotificationsByRecipient(
            String recipient);

    List<NotificationDTO> getUnreadNotifications(
            String recipient);

    long countUnreadNotifications(
            String recipient);

    // ==========================================================
    // NOTIFICATION WORKFLOW
    // ==========================================================

    NotificationDTO markAsRead(
            Long id);

    NotificationDTO markAllAsRead(
            String recipient);
}