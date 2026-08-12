package com.anandh.mes.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anandh.mes.dto.NotificationDTO;
import com.anandh.mes.entity.Notification;
import com.anandh.mes.enums.NotificationStatus;
import com.anandh.mes.exception.ResourceNotFoundException;
import com.anandh.mes.repository.NotificationRepository;
import com.anandh.mes.service.NotificationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    // ==========================================================
    // CREATE
    // ==========================================================

    @Override
    public NotificationDTO createNotification(
            NotificationDTO dto) {

        Notification notification = Notification.builder()
                .recipient(dto.getRecipient())
                .type(dto.getType())
                .message(dto.getMessage())
                .status(dto.getStatus() == null
                        ? NotificationStatus.UNREAD
                        : dto.getStatus())
                .referenceType(dto.getReferenceType())
                .referenceId(dto.getReferenceId())
                .build();

        Notification saved =
                notificationRepository.save(notification);

        return mapToDTO(saved);
    }

    // ==========================================================
    // READ
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public NotificationDTO getNotificationById(
            Long id) {

        Notification notification =
                notificationRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Notification not found"));

        return mapToDTO(notification);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationDTO> getAllNotifications() {

        return notificationRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ==========================================================
    // DELETE
    // ==========================================================

    @Override
    public void deleteNotification(Long id) {

        Notification notification =
                notificationRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Notification not found"));

        notificationRepository.delete(notification);
    }

    // ==========================================================
    // USER NOTIFICATIONS
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<NotificationDTO> getNotificationsByRecipient(
            String recipient) {

        return notificationRepository
                .findByRecipientOrderByCreatedAtDesc(recipient)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationDTO> getUnreadNotifications(
            String recipient) {

        return notificationRepository
                .findByRecipientAndStatusOrderByCreatedAtDesc(
                        recipient,
                        NotificationStatus.UNREAD)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public long countUnreadNotifications(
            String recipient) {

        return notificationRepository.countByRecipientAndStatus(
                recipient,
                NotificationStatus.UNREAD);
    }

    // ==========================================================
    // NOTIFICATION WORKFLOW
    // ==========================================================

    @Override
    public NotificationDTO markAsRead(Long id) {

        Notification notification =
                notificationRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Notification not found"));

        notification.setStatus(NotificationStatus.READ);
        notification.setReadAt(LocalDateTime.now());

        return mapToDTO(
                notificationRepository.save(notification));
    }

    @Override
    public NotificationDTO markAllAsRead(
            String recipient) {

        List<Notification> notifications =
                notificationRepository
                        .findByRecipientAndStatusOrderByCreatedAtDesc(
                                recipient,
                                NotificationStatus.UNREAD);

        LocalDateTime now = LocalDateTime.now();

        notifications.forEach(notification -> {
            notification.setStatus(NotificationStatus.READ);
            notification.setReadAt(now);
        });

        notificationRepository.saveAll(notifications);

        return notifications.isEmpty()
                ? null
                : mapToDTO(notifications.get(0));
    }

    // ==========================================================
    // DTO MAPPING
    // ==========================================================

    private NotificationDTO mapToDTO(
            Notification notification) {

        return NotificationDTO.builder()
                .notificationId(
                        notification.getNotificationId())
                .recipient(
                        notification.getRecipient())
                .type(
                        notification.getType())
                .message(
                        notification.getMessage())
                .status(
                        notification.getStatus())
                .referenceType(
                        notification.getReferenceType())
                .referenceId(
                        notification.getReferenceId())
                .createdAt(
                        notification.getCreatedAt())
                .readAt(
                        notification.getReadAt())
                .build();
    }
}