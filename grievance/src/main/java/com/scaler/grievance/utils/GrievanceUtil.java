package com.scaler.grievance.utils;

import com.scaler.grievance.entities.User;
import com.scaler.shared.dtos.UserResponseDto;

public class GrievanceUtil {


    public static UserResponseDto mapUserToResponseDto(User user) {
        if (user == null) {
            return null; // Handle the case where the user is null
        }

        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(user.getId());
        responseDto.setUsername(user.getUsername());
        responseDto.setRole(user.getRole());

        return responseDto;
    }
}
