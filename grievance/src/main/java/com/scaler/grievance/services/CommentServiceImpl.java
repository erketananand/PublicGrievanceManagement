package com.scaler.grievance.services;

import com.scaler.grievance.models.Comment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private Long commentId = 1L;

    private GrievanceService grievanceService;

    CommentServiceImpl(GrievanceService grievanceService) {
        this.grievanceService = grievanceService;
    }
    public Comment createComment(Long grievanceId, Comment comment) {
        // Simulate creating a new comment with dummy data
        comment.setId(commentId++);
        grievanceService.addComment(grievanceId, comment);
        return comment;
    }

    public List<Comment> getCommentsForGrievance(Long grievanceId) {
        // Simulate retrieving comments for a grievance with dummy data
        return grievanceService.getComments(grievanceId);
    }
}
