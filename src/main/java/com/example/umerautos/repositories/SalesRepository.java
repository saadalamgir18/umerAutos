package com.example.umerautos.repositories;

import com.example.umerautos.entities.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SalesRepository extends JpaRepository<Sales, UUID> {
}
