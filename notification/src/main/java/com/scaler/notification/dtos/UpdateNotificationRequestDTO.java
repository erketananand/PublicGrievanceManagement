package com.scaler.notification.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.scaler.notification.enums.NotificationStatus;
import com.scaler.notification.utils.NotificationStatusDeserializer;
import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class UpdateNotificationRequestDTO {
    private Long id;
    @JsonDeserialize(using = NotificationStatusDeserializer.class)
    private NotificationStatus status;

}
