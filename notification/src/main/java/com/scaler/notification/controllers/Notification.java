package com.scaler.notification.controllers;

import com.scaler.notification.message.queues.NotificationEventService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1//notification")
public class Notification {
    private NotificationEventService notificationEventService;
    Notification(NotificationEventService notificationEventService){
        this.notificationEventService = notificationEventService;
    }
}
