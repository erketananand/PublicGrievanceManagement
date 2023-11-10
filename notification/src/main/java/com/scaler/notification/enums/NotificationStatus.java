package com.scaler.notification.enums;


public enum NotificationStatus {
    PENDING, // Notification could not be created.
    CREATED, // Notification was successfully created
    SENT, // Notification was successfully sent by email. (Email being primary channel, we are not considering SMS status here)
    READ, // Notification was read by the recipient.
    FAILED, // Notification could not be sent via email. (Email being primary channel, we are not considering SMS status here)
    ARCHIVED // Notification archived
}

