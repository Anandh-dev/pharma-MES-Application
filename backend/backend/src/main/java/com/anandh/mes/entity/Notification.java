package com.anandh.mes.entity;

import java.time.LocalDateTime;

import com.anandh.mes.enums.NotificationStatus;
import com.anandh.mes.enums.NotificationType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @Column(nullable = false)
    private String recipient;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;

    @Column(nullable = false, length = 500)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationStatus status;

    private String referenceType;

    private Long referenceId;

    private LocalDateTime createdAt;

    private LocalDateTime readAt;

    @PrePersist
    public void onCreate() {

        createdAt = LocalDateTime.now();

        if (status == null) {
            status = NotificationStatus.UNREAD;
        }
    }
}