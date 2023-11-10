package com.scaler.notification.dtos;

import com.scaler.notification.enums.NotificationStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter  @Getter

public class NotificationDTO {
    private Long id;
    private String email;
    private String phoneNumber;
    private String message;
    private LocalDateTime createdAt;
    private LocalDateTime  updatedAt;
    private NotificationStatus status;
    private String emailStatus;
    private String smsStatus;
    private String emailContent;
    private String smsContent;
}
