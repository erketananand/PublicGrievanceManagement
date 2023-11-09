package com.scaler.grievance.message.queues;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class NotificationMessage implements Serializable {
    private String email;
    private String phoneNumber;
    private String subject;
    private String message;

    @Override
    public String toString() {
        return "NotificationMessage{" +
                "email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
