package com.scaler.notification.controllers;

import com.scaler.notification.dtos.CreateNotificationRequestDTO;
import com.scaler.notification.dtos.NotificationDTO;
import com.scaler.notification.dtos.UpdateNotificationRequestDTO;
import com.scaler.notification.exceptions.InvalidEmailException;
import com.scaler.notification.services.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final  NotificationService notificationService;

    public NotificationController(NotificationService notificationService ) {
        this.notificationService = notificationService;

    }

    @PostMapping()
    public ResponseEntity<NotificationDTO> createNotification(@Validated  @RequestBody CreateNotificationRequestDTO requestDTO) throws InvalidEmailException {
        NotificationDTO createdNotification = notificationService.createNotification(requestDTO);
        return new ResponseEntity<>(createdNotification, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<NotificationDTO>> listNotifications() {
        List<NotificationDTO> notifications = notificationService.listNotifications();
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @GetMapping("/{notificationId}")
    public ResponseEntity<NotificationDTO> getNotification(@PathVariable Long notificationId) {
        NotificationDTO notification = notificationService.getNotificationDetails(notificationId);
        return new ResponseEntity<>(notification, HttpStatus.OK);
    }

    @PutMapping("/{notificationId}")
    public ResponseEntity<NotificationDTO> updateNotificationStatus(
            @PathVariable Long notificationId,
            @RequestBody UpdateNotificationRequestDTO requestDTO) throws InvalidEmailException {
        NotificationDTO updatedNotification = notificationService.updateNotificationStatus(notificationId, requestDTO);
        return new ResponseEntity<>(updatedNotification, HttpStatus.OK);
    }

    @PutMapping("{notificationId}/archive")
    public ResponseEntity<NotificationDTO> archiveNotification(@PathVariable Long notificationId) throws InvalidEmailException {
        System.out.println("Request received to archive notification with id: " + notificationId);
        NotificationDTO updatedNotification = notificationService.archiveNotification(notificationId);
        return new ResponseEntity<>(updatedNotification, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<NotificationDTO>> listNotificationsForUser(@RequestParam String recipient) {
        List<NotificationDTO> notifications = notificationService.listNotificationsForUser(recipient);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

}