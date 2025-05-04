package com.example.umerautos.repositories;

import com.example.umerautos.entities.Suppliers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SuppliersRepository extends JpaRepository<Suppliers, UUID> {
}
