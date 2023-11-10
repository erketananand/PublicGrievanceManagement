package com.scaler.notification.dtos;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import org.jetbrains.annotations.NotNull;



@Setter
@Getter
public class CreateNotificationRequestDTO {
    @NotNull@Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "(\\+91)?[0-9]{10}", message = "Invalid phone number format. Valid phone numbers should Numbers  start with +91 followed by 10 digits.")
    private String phoneNumber;

    private String message;
}

