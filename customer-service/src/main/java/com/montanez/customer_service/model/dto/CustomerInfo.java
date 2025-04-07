package com.montanez.customer_service.model.dto;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerInfo {
    private UUID id;

    private String email;

    private String firstName;

    private String lastName;

    private LocalDate dob;
}
