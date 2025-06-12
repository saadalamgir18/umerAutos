package com.example.umerautos.services;

import com.example.umerautos.dto.PaginatedResponseDTO;
import com.example.umerautos.dto.SalesSummaryRequestDTO;
import com.example.umerautos.dto.SalesSummaryResponseDTO;
import com.example.umerautos.dto.SalesSummaryUpdate;
import com.example.umerautos.entities.PaymentStatus;

import java.util.UUID;

public interface SalesSummaryService {

    public SalesSummaryResponseDTO saveOne(SalesSummaryRequestDTO salesSummaryRequestDTO);

    public PaginatedResponseDTO<SalesSummaryResponseDTO> findAll(int page, int limit);

    public PaginatedResponseDTO<SalesSummaryResponseDTO> findSalesSummary(int page, int limit, PaymentStatus paymentStatus);

    SalesSummaryResponseDTO findSalesSummaryById(UUID id);

    SalesSummaryResponseDTO updateSaleSummaryById(UUID id, SalesSummaryUpdate request);

    SalesSummaryResponseDTO updateSaleSummaryById(UUID id);
}
