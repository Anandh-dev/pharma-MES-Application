package com.anandh.mes.dto;

import java.time.LocalDateTime;

import com.anandh.mes.enums.AuditAction;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditTrailDTO {

    private Long auditTrailId;

    @NotNull
    private AuditAction action;

    @NotBlank
    private String username;

    @NotBlank
    private String entityName;

    @NotNull
    private Long entityId;

    private String fieldName;

    private String oldValue;

    private String newValue;

    private String description;

    private LocalDateTime timestamp;

    private String ipAddress;

    private String remarks;

}