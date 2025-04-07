package com.montanez.customer_service.model.api;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiError {
    private String message;
    private LocalDateTime timestamp;
    private int status;
}
