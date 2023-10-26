package com.scaler.user.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class User {
    private Long userId;
    private String fullName;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String country;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private List<Role> roles;
    private List<UserSession> sessions;
    private List<PasswordResetRequest> passwordResetRequests;
}
