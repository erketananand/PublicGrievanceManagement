package com.scaler.grievance.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "grievances")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Grievance extends BaseModel{
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private GrievanceCategory category;

    @Enumerated(EnumType.STRING)
    private GrievanceSubCategory subCategory;

    @Enumerated(EnumType.STRING)
    private GrievancePriority priority;

    @Enumerated(EnumType.STRING)
    private GrievanceStatus status;

    @ManyToOne
    private User user;

    @ManyToOne
    private User admin;

    @OneToMany(mappedBy = "grievance", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
}
