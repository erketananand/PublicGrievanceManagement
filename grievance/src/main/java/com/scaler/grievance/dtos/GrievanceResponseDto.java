package com.scaler.grievance.dtos;

import com.scaler.grievance.constants.GrievanceCategory;
import com.scaler.grievance.constants.GrievancePriority;
import com.scaler.grievance.constants.GrievanceStatus;
import com.scaler.grievance.constants.GrievanceSubCategory;

import com.scaler.shared.dtos.UserResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrievanceResponseDto {
    private Long id;
    private GrievanceStatus status;
    private UserResponseDto admin;
    private String title;
    private String description;
    private GrievanceCategory category;
    private GrievanceSubCategory subCategory;
    private GrievancePriority priority;
}

