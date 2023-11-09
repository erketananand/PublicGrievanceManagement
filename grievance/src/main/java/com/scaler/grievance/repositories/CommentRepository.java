package com.scaler.grievance.repositories;

import com.scaler.grievance.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByGrievanceId(Long grievanceId);

    List<Comment> findByGrievance_Id(Long grievanceId);
}
