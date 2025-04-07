package com.montanez.customer_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.montanez.customer_service.model.dto.CreateCustomer;
import com.montanez.customer_service.model.dto.CustomerInfo;
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

}
