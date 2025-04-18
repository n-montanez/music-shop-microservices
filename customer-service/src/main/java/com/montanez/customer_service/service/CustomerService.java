package com.montanez.customer_service.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.montanez.customer_service.model.auth.CreateCustomer;
import com.montanez.customer_service.model.auth.LoginRequest;
import com.montanez.customer_service.model.auth.exceptions.EmailAlreadyExistsException;
import com.montanez.customer_service.model.auth.exceptions.InvalidCredentialsException;
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
        if (customerRepository.existsByEmail(newCustomer.getEmail()))
            throw new EmailAlreadyExistsException("Email already registered");

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

        if (!foundCustomer.isPresent())
            throw new InvalidCredentialsException("Email does not exist");

        Customer customer = foundCustomer.get();
        if (!encoder.matches(request.getPassword(), customer.getPasswordHash())) {
            throw new InvalidCredentialsException("Invalid email or password.");
        }

        return jwtService.generateToken(customer.getId());
    }

    public CustomerInfo profile(String authHeader) {
        String token = authHeader.substring(7);
        UUID id = UUID.fromString(jwtService.extractSubject(token));

        Optional<Customer> foundCustomer = customerRepository.findById(id);
        if (!foundCustomer.isPresent())
            throw new InvalidCredentialsException("Invalid Token. Email does not exist");

        Customer customer = foundCustomer.get();
        return CustomerInfo
                .builder()
                .id(customer.getId())
                .email(customer.getEmail())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .dob(customer.getDob())
                .build();
    }
}
