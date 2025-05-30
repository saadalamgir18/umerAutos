package com.example.umerautos.repositories;

import com.example.umerautos.entities.PaymentStatus;
import com.example.umerautos.entities.SalesSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SalesSummaryRepository extends JpaRepository<SalesSummary, UUID> {

    Page<SalesSummary> findByPaymentStatus(PaymentStatus paymentStatus, Pageable pageable);



}
