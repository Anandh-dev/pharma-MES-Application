package com.anandh.mes.dto;

import java.time.LocalDateTime;

import com.anandh.mes.enums.NotificationStatus;
import com.anandh.mes.enums.NotificationType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO {

    private Long notificationId;

    @NotBlank
    private String recipient;

    @NotNull
    private NotificationType type;

    @NotBlank
    private String message;

    private NotificationStatus status;

    private String referenceType;

    private Long referenceId;

    private LocalDateTime createdAt;

    private LocalDateTime readAt;
}