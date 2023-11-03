package com.scaler.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String fullName;
    private String username;
    private String email;
    private String password;
    private String phone;
    private String country;
}
