package com.scaler.grievance.models;

import jakarta.persistence.Entity;
import lombok.*;

@Entity(name = "addresses")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address extends BaseModel{
    private String street;
    private String city;
    private String state;
    private String country;
    private String pinCode;
}
