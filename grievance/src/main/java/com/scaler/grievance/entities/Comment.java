package com.scaler.grievance.entities;

import com.scaler.shared.entities.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity(name = "comments")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseEntity {
    @ManyToOne
    private Grievance grievance;

    @ManyToOne
    @JoinColumn(name = "user_id") // Make sure the column name matches your database schema
    private User user;
    private String commentMessage;
}

