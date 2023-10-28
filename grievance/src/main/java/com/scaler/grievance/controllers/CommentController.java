package com.scaler.grievance.controllers;
import com.scaler.grievance.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.scaler.grievance.models.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/grievances")
public class CommentController {

    private CommentService commentService;
    CommentController(CommentService commentService){
        this.commentService = commentService;
    }
    // Comment on Grievance
    @PostMapping("/{grievanceId}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment createComment(@PathVariable Long grievanceId, @RequestBody Comment comment) {
        return commentService.createComment(grievanceId, comment);
    }

    // Get Comments for Grievance
    @GetMapping("/{grievanceId}/comments")
    public ResponseEntity<List<Comment>> getCommentsForGrievance(@PathVariable Long grievanceId) {
        // Simulate retrieving comments for a grievance with dummy data
        List<Comment> grievanceComments = commentService.getCommentsForGrievance(grievanceId);

        if (!grievanceComments.isEmpty()) {
            return ResponseEntity.ok(grievanceComments);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
