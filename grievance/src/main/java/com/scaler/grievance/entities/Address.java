package com.scaler.grievance.entities;

import com.scaler.shared.entities.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;

@Entity(name = "addresses")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address extends BaseEntity {
    private String street;
    private String city;
    private String state;
    private String country;
    private String pinCode;
}
