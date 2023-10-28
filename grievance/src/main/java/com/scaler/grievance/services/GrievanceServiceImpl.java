package com.scaler.grievance.services;

import com.scaler.grievance.models.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GrievanceServiceImpl implements GrievanceService {

    private List<Grievance> grievances = new ArrayList<>(); // Dummy data

    public Grievance createGrievance(Grievance grievance) {
        // Simulate creating a new grievance with dummy data
        grievance.setId((long) (grievances.size() + 1));
        grievances.add(grievance);
        return grievance;
    }

    public Grievance getGrievanceById(Long grievanceId) {
        // Simulate retrieving a grievance by ID with dummy data
        return grievances.stream()
                .filter(g -> g.getId().equals(grievanceId))
                .findFirst()
                .orElse(null);
    }

    public List<Grievance> getAllGrievances() {
        // Return all grievances from the dummy data
        return grievances;
    }

    public List<Grievance> getGrievancesByUser(Long userId) {
        // Simulate retrieving grievances by user with dummy data
        return grievances.stream()
                .filter(g -> g.getUser().getId().equals(userId))
                .collect(Collectors.toList());
    }

    public List<Grievance> searchGrievances(GrievanceCategory category, GrievanceStatus status, GrievancePriority priority, String keyword) {
        // Simulate searching for grievances based on criteria with dummy data
        List<Grievance> matchingGrievances = new ArrayList<>();

        for (Grievance grievance : grievances) {
            if ((category == null || grievance.getCategory().equals(category))
                    && (status == null || grievance.getStatus().equals(status))
                    && (priority == null || grievance.getPriority().equals(priority))
                    && (keyword == null || grievance.getTitle().contains(keyword) || grievance.getDescription().contains(keyword))) {
                matchingGrievances.add(grievance);
            }
        }

        return matchingGrievances;
    }

    public void deleteGrievance(Long grievanceId) {
        // Simulate soft deleting a grievance with dummy data
        grievances.removeIf(g -> g.getId().equals(grievanceId));
    }

    public void addComment(Long grievanceId, Comment comment) {
        // Simulate adding a comment to a grievance with dummy data
        Grievance grievance = getGrievanceById(grievanceId);
        grievance.getComments().add(comment);
    }

    public List<Comment> getComments(Long grievanceId) {
        // Simulate retrieving comments for a grievance with dummy data
        Grievance grievance = getGrievanceById(grievanceId);
        return grievance.getComments();
    }
}

