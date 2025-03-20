package com.montanez.stock_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.montanez.stock_service.model.physical_copy.PhysicalCopy;

@Repository
public interface PhysicalCopyRepository extends JpaRepository<PhysicalCopy, UUID> {

}
