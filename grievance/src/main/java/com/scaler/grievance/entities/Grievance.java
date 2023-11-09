package com.scaler.grievance.entities;

import org.hibernate.annotations.Where;

import com.scaler.grievance.constants.GrievanceCategory;
import com.scaler.grievance.constants.GrievancePriority;
import com.scaler.grievance.constants.GrievanceStatus;
import com.scaler.grievance.constants.GrievanceSubCategory;
import com.scaler.shared.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "grievances")
@Where(clause = "status != 'DELETED'")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Grievance extends BaseEntity {
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
    @JoinColumn(name = "user_id")  // Define the appropriate column name for the user
    private User user;

    @ManyToOne
    @JoinColumn(name = "admin_id")  // Define the appropriate column name for the admin
    private User admin;

    @OneToMany(mappedBy = "grievance", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
}

