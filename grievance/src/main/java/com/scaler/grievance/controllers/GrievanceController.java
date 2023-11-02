package com.scaler.grievance.controllers;

import com.scaler.grievance.constants.GrievanceCategory;
import com.scaler.grievance.constants.GrievancePriority;
import com.scaler.grievance.constants.GrievanceStatus;
import com.scaler.grievance.constants.GrievanceSubCategory;
import com.scaler.grievance.dtos.GrievanceRequestDto;
import com.scaler.grievance.dtos.GrievanceResponseDto;
import com.scaler.grievance.entities.*;
import com.scaler.grievance.exceptions.GrievanceNotFoundException;
import com.scaler.grievance.services.GrievanceService;
import com.scaler.shared.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/grievances")
public class GrievanceController {
    private GrievanceService grievanceService;
    GrievanceController(GrievanceService grievanceService){
        this.grievanceService = grievanceService;
    }

    // Create Grievance
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrievanceResponseDto createGrievance(@RequestBody GrievanceRequestDto grievanceRequest, @RequestHeader("authentication") String jwtToken) throws NotFoundException {


        // Manually validate and extract user information from the JWT token and handle exception of invalid tokens as well
        String username = grievanceService.validateAndExtractUsernameFromToken(jwtToken);

        return grievanceService.createGrievance(grievanceRequest, username);
    }

    // Get Grievance by ID
    @GetMapping("/{grievanceId}")
    public ResponseEntity<GrievanceResponseDto> getGrievance(@PathVariable Long grievanceId) throws GrievanceNotFoundException {
        GrievanceResponseDto responseDto = grievanceService.getGrievanceById(grievanceId);
        return ResponseEntity.ok(responseDto);
    }

    // Get All Grievances
    @GetMapping
    public List<GrievanceResponseDto> getAllGrievances() {
        // Return all grievances from the dummy data
        return grievanceService.getAllGrievances();
    }

    // Get Grievances by User
    @GetMapping("/user/{userId}")
    public List<GrievanceResponseDto> getGrievancesByUser(@PathVariable Long userId) {
        List<GrievanceResponseDto> responseDtos = grievanceService.getGrievancesByUser(userId);
        return responseDtos;
    }

    // Get Grievances by User
    @GetMapping("/unassigned")
    public List<GrievanceResponseDto> getUnassignedGrievances() {
        List<GrievanceResponseDto> responseDtos = grievanceService.getUnassignedGrievances();
        return responseDtos;
    }

    // Search Grievances
    @GetMapping("/search")
    public List<GrievanceResponseDto> searchGrievances(
            @RequestParam(value = "category", required = false) GrievanceCategory category,
            @RequestParam(value = "subCategory", required = false) GrievanceSubCategory subCategory,
            @RequestParam(value = "status", required = false) GrievanceStatus status,
            @RequestParam(value = "priority", required = false) GrievancePriority priority,
            @RequestParam(value = "keyword", required = false) String keyword) {

        return grievanceService.searchGrievances(category, subCategory, status, priority, keyword);
    }

    // Delete Grievance
    @DeleteMapping("/{grievanceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGrievance(@PathVariable Long grievanceId) {
        grievanceService.deleteGrievance(grievanceId);
    }
}
