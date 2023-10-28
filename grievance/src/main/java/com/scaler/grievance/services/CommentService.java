package com.scaler.grievance.services;

import com.scaler.grievance.models.Comment;

import java.util.List;

public interface CommentService {
    Comment createComment(Long grievanceId, Comment comment);

    List<Comment> getCommentsForGrievance(Long grievanceId);
}

