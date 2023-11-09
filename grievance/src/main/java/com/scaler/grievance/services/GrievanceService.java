package com.scaler.grievance.services;

import com.scaler.grievance.constants.GrievanceCategory;
import com.scaler.grievance.constants.GrievancePriority;
import com.scaler.grievance.constants.GrievanceStatus;
import com.scaler.grievance.constants.GrievanceSubCategory;
import com.scaler.grievance.dtos.GrievanceRequestDto;
import com.scaler.grievance.dtos.GrievanceResponseDto;
import com.scaler.grievance.entities.*;
import com.scaler.grievance.exceptions.GrievanceNotFoundException;
import com.scaler.shared.exceptions.NotFoundException;

import java.util.List;

public interface GrievanceService {
    String validateAndExtractUsernameFromToken(String jwtToken);
    GrievanceResponseDto createGrievance(GrievanceRequestDto grievanceRequest, String username) throws NotFoundException;

    GrievanceResponseDto getGrievanceById(Long grievanceId) throws GrievanceNotFoundException;

    List<GrievanceResponseDto> getAllGrievances();

    List<GrievanceResponseDto> getGrievancesByUser(Long userId);
    List<GrievanceResponseDto> getUnassignedGrievances();

    List<GrievanceResponseDto> searchGrievances(GrievanceCategory category, GrievanceSubCategory subCategory, GrievanceStatus status, GrievancePriority priority, String keyword);

    int deleteGrievance(Long grievanceId);

}

