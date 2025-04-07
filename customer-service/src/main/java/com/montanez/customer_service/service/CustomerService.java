package com.montanez.customer_service.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.montanez.customer_service.model.auth.CreateCustomer;
import com.montanez.customer_service.model.auth.LoginRequest;
import com.montanez.customer_service.model.customer.Customer;
import com.montanez.customer_service.model.customer.dto.CustomerInfo;
import com.montanez.customer_service.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private final JwtService jwtService;

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

    public String login(LoginRequest request) {
        Optional<Customer> foundCustomer = customerRepository.findByEmail(request.getEmail());

        if (!foundCustomer.isPresent()) {
            // TODO : Handle not found
            return null;
        }

        Customer customer = foundCustomer.get();
        if (!encoder.matches(request.getPassword(), customer.getPasswordHash())) {
            // TODO : Handle invalid credentials
            return null;
        }

        return jwtService.generateToken(customer.getEmail());
    }
}
