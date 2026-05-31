package com.example.umerautos.repositories;

import com.example.umerautos.entities.SalesPerson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SalesPerson, Long> {
    Optional<SalesPerson> findSalesPersonByEmail(String email);
}
