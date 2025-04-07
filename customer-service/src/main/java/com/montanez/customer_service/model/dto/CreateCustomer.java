package com.montanez.customer_service.model.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomer {
    @Email
    private String email;

    private String firstName;

    private String lastName;

    private LocalDate dob;

    @Size(min = 8, message = "Invalid password")
    private String password;
}
