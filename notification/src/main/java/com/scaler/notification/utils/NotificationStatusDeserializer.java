package com.scaler.notification.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.scaler.notification.enums.NotificationStatus;
import com.scaler.notification.exceptions.CustomInvalidStatusException;

import java.io.IOException;


public class NotificationStatusDeserializer extends JsonDeserializer<NotificationStatus> {
    @Override
    public NotificationStatus deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String value = jp.getText().toUpperCase();
        try {
            return NotificationStatus.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new CustomInvalidStatusException("Invalid Status value: " + value + ". Accepted values are: SENT, READ, FAILED, ARCHIVED");
        }
    }
}
