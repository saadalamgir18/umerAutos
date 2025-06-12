package com.example.umerautos.repositories;

import com.example.umerautos.entities.SalesPerson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<SalesPerson, UUID> {
    Optional<SalesPerson> findSalesPersonByEmail(String email);
}
