package com.scaler.grievance.dtos;

import com.scaler.grievance.constants.GrievanceCategory;
import com.scaler.grievance.constants.GrievancePriority;
import com.scaler.grievance.constants.GrievanceSubCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrievanceRequestDto {
    private String title;
    private String description;
    private GrievanceCategory category;
    private GrievanceSubCategory subCategory;
    private GrievancePriority priority;
}

