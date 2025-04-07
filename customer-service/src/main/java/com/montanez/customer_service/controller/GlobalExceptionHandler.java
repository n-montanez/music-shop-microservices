package com.montanez.customer_service.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.montanez.customer_service.model.api.ApiError;
import com.montanez.customer_service.model.auth.exceptions.EmailAlreadyExistsException;
import com.montanez.customer_service.model.auth.exceptions.InvalidCredentialsException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiError(ex.getMessage(), LocalDateTime.now(), HttpStatus.CONFLICT.value()));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiError> handleInvalidCredentials(InvalidCredentialsException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ApiError(ex.getMessage(), LocalDateTime.now(), HttpStatus.UNAUTHORIZED.value()));
    }

    @ExceptionHandler(exception = Exception.class)
    public ResponseEntity<ApiError> handleStandardException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError("An error occurred: " + ex.getMessage(), LocalDateTime.now(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

}
