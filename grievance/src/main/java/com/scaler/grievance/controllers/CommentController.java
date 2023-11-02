package com.scaler.grievance.controllers;
import com.scaler.grievance.dtos.CommentRequestDto;
import com.scaler.grievance.dtos.CommentResponseDto;
import com.scaler.grievance.dtos.GetAllCommentsResponseDto;
import com.scaler.grievance.exceptions.GrievanceNotFoundException;
import com.scaler.grievance.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.scaler.grievance.entities.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/grievances")
public class CommentController {

    private CommentService commentService;
    CommentController(CommentService commentService){
        this.commentService = commentService;
    }
    // Comment on Grievance
    @PostMapping("/{grievanceId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDto createComment(@PathVariable Long grievanceId, @RequestBody CommentRequestDto comment) throws GrievanceNotFoundException {
        CommentResponseDto responseDto = commentService.createComment(grievanceId, comment);
        return responseDto;
    }

    // Get Comments for Grievance
    @GetMapping("/{grievanceId}/comments")
    public ResponseEntity<List<GetAllCommentsResponseDto>> getCommentsForGrievance(@PathVariable Long grievanceId) {
        List<GetAllCommentsResponseDto> responseDtos = commentService.getCommentsForGrievance(grievanceId);
        if (!responseDtos.isEmpty()) {
            return ResponseEntity.ok(responseDtos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
