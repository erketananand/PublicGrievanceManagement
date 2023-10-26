package com.scaler.user.models;

import java.sql.Timestamp;

public class UserSession {
    private Long sessionId;
    private User user;
    private String token;
    private Timestamp expirationTime;
}
