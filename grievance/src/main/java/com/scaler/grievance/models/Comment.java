package com.scaler.grievance.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity(name = "comments")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseModel {
    @ManyToOne
    private Grievance grievance;

    @ManyToOne
    private User user;
    private String commentText;
}

