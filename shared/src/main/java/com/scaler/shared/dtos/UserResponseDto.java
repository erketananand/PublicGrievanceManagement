package com.scaler.shared.dtos;

import com.scaler.shared.constants.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private Long id;
    private String username;
    private UserRole role;
}
