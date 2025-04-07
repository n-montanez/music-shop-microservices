package com.montanez.customer_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.montanez.customer_service.model.auth.CreateCustomer;
import com.montanez.customer_service.model.auth.LoginRequest;
import com.montanez.customer_service.model.auth.LoginResponse;
import com.montanez.customer_service.model.customer.dto.CustomerInfo;
import com.montanez.customer_service.service.CustomerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerInfo> register(@Valid @RequestBody CreateCustomer customer) {
        return new ResponseEntity<>(customerService.register(customer), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        String token = customerService.login(loginRequest);
        return new ResponseEntity<>(new LoginResponse(token), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<CustomerInfo> getProfile(@RequestHeader("Authorization") String authHeader) {
        return new ResponseEntity<>(customerService.profile(authHeader), HttpStatus.OK);
    }

}
