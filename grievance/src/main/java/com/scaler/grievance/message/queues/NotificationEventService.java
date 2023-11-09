package com.scaler.grievance.message.queues;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationEventService {
    private final RabbitTemplate rabbitTemplate;


    public NotificationEventService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendNotification(String email, String phoneNumber, String subject, String message) {
        NotificationMessage notificationMessage = new NotificationMessage();
        notificationMessage.setEmail(email);
        notificationMessage.setPhoneNumber(phoneNumber);
        notificationMessage.setSubject(subject);
        notificationMessage.setMessage(message);
        rabbitTemplate.convertAndSend("grievance", notificationMessage);
    }
}
