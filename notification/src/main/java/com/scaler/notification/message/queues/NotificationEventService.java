package com.scaler.notification.message.queues;

import com.scaler.grievance.message.queues.NotificationMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationEventService {
    @RabbitListener(queues = "grievance")
    public void receiveNotification(NotificationMessage notificationMessage) {
        System.out.println("Received notification message: " + notificationMessage.toString());
    }
}
