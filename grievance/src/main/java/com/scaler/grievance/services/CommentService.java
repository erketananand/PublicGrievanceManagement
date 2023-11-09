package com.scaler.grievance.services;

import com.scaler.grievance.dtos.CommentRequestDto;
import com.scaler.grievance.dtos.CommentResponseDto;
import com.scaler.grievance.dtos.GetAllCommentsResponseDto;
import com.scaler.grievance.entities.Comment;
import com.scaler.grievance.exceptions.GrievanceNotFoundException;

import java.util.List;

public interface CommentService {
    CommentResponseDto createComment(Long grievanceId, CommentRequestDto commentRequest) throws GrievanceNotFoundException;

    List<GetAllCommentsResponseDto> getCommentsForGrievance(Long grievanceId);
}

