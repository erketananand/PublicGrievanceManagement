package com.scaler.grievance.entities;

import com.scaler.shared.constants.UserRole;
import com.scaler.shared.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;
}
