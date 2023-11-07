package com.scaler.user.models;

import com.scaler.user.utilities.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Users")
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long userId;
    private String fullName;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    private String password;
    private String phone;
    private String country;
    @Enumerated(EnumType.STRING)
    private RoleType roleType;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp lastLoginAt;
}