package com.example.umerautos.repositories;

import com.example.umerautos.entities.Brands;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BrandsRepository extends JpaRepository<Brands, UUID> {
}
