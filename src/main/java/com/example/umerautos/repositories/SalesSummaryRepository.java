package com.example.umerautos.repositories;

import com.example.umerautos.entities.PaymentStatus;
import com.example.umerautos.entities.SalesSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesSummaryRepository extends JpaRepository<SalesSummary, Long> {

    Page<SalesSummary> findByCustomer_IdAndSaleItems_PaymentStatus(Long customer_id, PaymentStatus paymentStatus, Pageable pageable);
    
    Page<SalesSummary> findBySaleItems_PaymentStatus(PaymentStatus paymentStatus, Pageable pageable);


}
