package com.montanez.customer_service.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.montanez.customer_service.model.Customer;
import com.montanez.customer_service.model.dto.CreateCustomer;
import com.montanez.customer_service.model.dto.CustomerInfo;
import com.montanez.customer_service.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public CustomerInfo register(CreateCustomer newCustomer) {
        Customer customerToSave = Customer.builder()
                .email(newCustomer.getEmail())
                .firstName(newCustomer.getFirstName())
                .lastName(newCustomer.getLastName())
                .dob(newCustomer.getDob())
                .passwordHash(encoder.encode(newCustomer.getPassword()))
                .build();
        Customer saved = customerRepository.save(customerToSave);
        return CustomerInfo.builder()
                .id(saved.getId())
                .firstName(saved.getFirstName())
                .lastName(saved.getLastName())
                .email(saved.getEmail())
                .dob(saved.getDob())
                .build();
    }
}
