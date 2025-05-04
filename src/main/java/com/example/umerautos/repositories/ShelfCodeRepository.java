package com.example.umerautos.repositories;

import com.example.umerautos.entities.ShelfCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShelfCodeRepository extends JpaRepository<ShelfCode, UUID> {
}
