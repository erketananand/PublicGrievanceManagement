package com.scaler.grievance.controllers;

import com.scaler.grievance.models.*;
import com.scaler.grievance.services.GrievanceService;
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
    public Grievance createGrievance(@RequestBody Grievance grievance) {
        return grievanceService.createGrievance(grievance);
    }

    // Get Grievance by ID
    @GetMapping("/{grievanceId}")
    public ResponseEntity<Grievance> getGrievance(@PathVariable Long grievanceId) {
        // Simulate retrieval of a grievance by ID with dummy data
        Grievance grievance = grievanceService.getGrievanceById(grievanceId);

        if (grievance != null) {
            return ResponseEntity.ok(grievance);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get All Grievances
    @GetMapping
    public List<Grievance> getAllGrievances() {
        // Return all grievances from the dummy data
        return grievanceService.getAllGrievances();
    }

    // Get Grievances by User
    @GetMapping("/user/{userId}")
    public List<Grievance> getGrievancesByUser(@PathVariable Long userId) {
        // Simulate retrieval of grievances by user with dummy data
        List<Grievance> userGrievances = grievanceService.getGrievancesByUser(userId);
        return userGrievances;
    }

    // Search Grievances
    @GetMapping("/search")
    public List<Grievance> searchGrievances(
            @RequestParam(value = "category", required = false) GrievanceCategory category,
            @RequestParam(value = "status", required = false) GrievanceStatus status,
            @RequestParam(value = "priority", required = false) GrievancePriority priority,
            @RequestParam(value = "keyword", required = false) String keyword) {

        return grievanceService.searchGrievances(category, status, priority, keyword);
    }

    // Delete Grievance
    @DeleteMapping("/{grievanceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGrievance(@PathVariable Long grievanceId) {
        // Simulate soft deleting a grievance with dummy data
        grievanceService.deleteGrievance(grievanceId);
    }
}
