package com.scaler.notification.services;

import com.scaler.notification.dtos.CreateNotificationRequestDTO;
import com.scaler.notification.dtos.NotificationDTO;
import com.scaler.notification.dtos.UpdateNotificationRequestDTO;
import com.scaler.notification.exceptions.InvalidEmailException;

import java.util.List;

public interface NotificationService {
    public NotificationDTO createNotification(CreateNotificationRequestDTO requestDTO) throws InvalidEmailException;

    List<NotificationDTO> listNotifications();

    public NotificationDTO getNotificationDetails(Long notificationId);

    public NotificationDTO updateNotificationStatus(Long notificationId, UpdateNotificationRequestDTO requestDTO) throws InvalidEmailException;

    NotificationDTO archiveNotification(Long notificationId) throws InvalidEmailException;

    List<NotificationDTO> listNotificationsForUser(String recipient);

}