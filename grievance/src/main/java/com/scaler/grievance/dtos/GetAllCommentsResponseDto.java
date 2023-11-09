package com.scaler.grievance.dtos;

import java.time.LocalDateTime;

import com.scaler.shared.dtos.UserResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetAllCommentsResponseDto {
    private Long id;
    private String commentMessage;
    private LocalDateTime commentedDateTime;
    private UserResponseDto commentedBy;
}
