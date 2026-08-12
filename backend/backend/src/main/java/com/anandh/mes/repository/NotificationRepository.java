package com.anandh.mes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anandh.mes.entity.Notification;
import com.anandh.mes.enums.NotificationStatus;
import com.anandh.mes.enums.NotificationType;

public interface NotificationRepository
        extends JpaRepository<Notification, Long> {

    List<Notification> findByRecipientOrderByCreatedAtDesc(
            String recipient);

    List<Notification> findByRecipientAndStatusOrderByCreatedAtDesc(
            String recipient,
            NotificationStatus status);

    List<Notification> findByType(
            NotificationType type);

    long countByRecipientAndStatus(
            String recipient,
            NotificationStatus status);
}