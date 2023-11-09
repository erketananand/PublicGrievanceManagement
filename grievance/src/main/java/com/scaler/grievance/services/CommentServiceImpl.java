package com.scaler.grievance.services;

import com.scaler.grievance.dtos.CommentRequestDto;
import com.scaler.grievance.dtos.CommentResponseDto;
import com.scaler.grievance.dtos.GetAllCommentsResponseDto;
import com.scaler.grievance.entities.Comment;
import com.scaler.grievance.entities.Grievance;
import com.scaler.grievance.exceptions.GrievanceNotFoundException;
import com.scaler.grievance.repositories.CommentRepository;
import com.scaler.grievance.repositories.GrievanceRepository;
import com.scaler.grievance.utils.GrievanceUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final GrievanceRepository grievanceRepository;
    private final CommentRepository commentRepository;

    CommentServiceImpl(GrievanceRepository grievanceRepository, CommentRepository commentRepository) {
        this.grievanceRepository = grievanceRepository;
        this.commentRepository = commentRepository;
    }


    public CommentResponseDto createComment(Long grievanceId, CommentRequestDto commentRequest) throws GrievanceNotFoundException {
        Comment comment = new Comment();
        Optional<Grievance> optionalGrievance = grievanceRepository.findById(grievanceId);
        if(optionalGrievance.isEmpty()) {
            throw new GrievanceNotFoundException("Grievance not found with id: " + grievanceId);
        }
        Grievance grievance = optionalGrievance.get();
        comment.setGrievance(grievance);
        comment.setCommentMessage(commentRequest.getCommentMessage());
        comment.setUser(grievance.getUser());

        commentRepository.save(comment);

        CommentResponseDto responseDto = new CommentResponseDto();
        responseDto.setCommentId(comment.getId());
        responseDto.setCommentMessage(comment.getCommentMessage());

        return responseDto;
    }

    public List<GetAllCommentsResponseDto> getCommentsForGrievance(Long grievanceId) {
        List<Comment> comments = commentRepository.findByGrievance_Id(grievanceId);

        return comments.stream()
                .map(this::mapCommentToResponseDto)
                .collect(Collectors.toList());
    }

    private GetAllCommentsResponseDto mapCommentToResponseDto(Comment comment) {
        GetAllCommentsResponseDto responseDto = new GetAllCommentsResponseDto();
        responseDto.setId(comment.getId());
        responseDto.setCommentMessage(comment.getCommentMessage());
        responseDto.setCommentedDateTime(comment.getCreatedAt());
        responseDto.setCommentedBy(GrievanceUtil.mapUserToResponseDto(comment.getUser()));
        return responseDto;
    }
}
