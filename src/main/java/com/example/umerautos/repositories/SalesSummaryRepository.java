package com.example.umerautos.repositories;

import com.example.umerautos.entities.SalesSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SalesSummaryRepository extends JpaRepository<SalesSummary, UUID> {

}
