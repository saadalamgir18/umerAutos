package com.example.umerautos.repositories;

import com.example.umerautos.entities.CompatibleModels;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompatibleModelRepository extends JpaRepository<CompatibleModels, Long> {
}
