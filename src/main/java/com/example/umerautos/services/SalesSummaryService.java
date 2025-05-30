package com.example.umerautos.services;

import com.example.umerautos.dto.PaginatedResponseDTO;
import com.example.umerautos.dto.SalesSummaryRequestDTO;
import com.example.umerautos.dto.SalesSummaryResponseDTO;
import com.example.umerautos.entities.PaymentStatus;

import java.util.List;

public interface SalesSummaryService {

    public SalesSummaryResponseDTO saveOne(SalesSummaryRequestDTO salesSummaryRequestDTO);
    public PaginatedResponseDTO<SalesSummaryResponseDTO> findAll(int page, int limit);
    public PaginatedResponseDTO<SalesSummaryResponseDTO> finadSalesSummary(int page, int limit, PaymentStatus paymentStatus);

}
