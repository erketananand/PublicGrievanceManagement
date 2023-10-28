package com.scaler.grievance.services;

import com.scaler.grievance.models.*;

import java.util.List;

public interface GrievanceService {
    Grievance createGrievance(Grievance grievance);

    Grievance getGrievanceById(Long grievanceId);

    List<Grievance> getAllGrievances();

    List<Grievance> getGrievancesByUser(Long userId);

    List<Grievance> searchGrievances(GrievanceCategory category, GrievanceStatus status, GrievancePriority priority, String keyword);

    void deleteGrievance(Long grievanceId);
    void addComment(Long grievanceId, Comment comment);
    List<Comment> getComments(Long grievanceId);
}

