package com.montanez.customer_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.montanez.customer_service.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

}
