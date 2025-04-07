package com.montanez.customer_service.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.montanez.customer_service.model.customer.Customer;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    public Optional<Customer> findByEmail(String email);

    public boolean existsByEmail(String email);
}
