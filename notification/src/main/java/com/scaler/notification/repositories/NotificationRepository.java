package com.scaler.notification.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.scaler.notification.entities.Notification;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByEmailOrPhoneNumber(String recipient, String recipient1);

    List<Notification> findByEmail(String recipient);
}
