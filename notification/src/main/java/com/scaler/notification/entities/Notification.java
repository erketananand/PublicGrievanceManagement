package com.scaler.notification.entities;

import com.scaler.notification.enums.NotificationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;

@Getter@Setter
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notification_id;

    @NotNull
    private String email;
    private String phoneNumber;

    @NotNull
    private String message;
    // Default value -. sys time when notification is created.
    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime created_at = LocalDateTime.now();

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updated_at = LocalDateTime.now();
    @Enumerated(EnumType.STRING)
    private NotificationStatus status=NotificationStatus.PENDING;

    @Column(name = "email_sent", columnDefinition = "TINYINT DEFAULT 0")
    private Boolean isEmailSent = false;

    @Column(name = "sms_sent", columnDefinition = "TINYINT DEFAULT 0")
    private Boolean isSmsSent = false;


}
