package com.example.umerautos.services;

import com.example.umerautos.dto.PaginatedResponseDTO;
import com.example.umerautos.dto.SalesSummaryRequestDTO;
import com.example.umerautos.dto.SalesSummaryResponseDTO;
import com.example.umerautos.dto.UpdateDebtorsSales;
import com.example.umerautos.entities.PaymentStatus;

public interface SalesSummaryService {

    public SalesSummaryResponseDTO saveOne(SalesSummaryRequestDTO salesSummaryRequestDTO);

    public PaginatedResponseDTO<SalesSummaryResponseDTO> findAll(int page, int limit);

    public PaginatedResponseDTO<SalesSummaryResponseDTO> findSalesSummary(int page, int limit, int customerId, PaymentStatus paymentStatus);

    SalesSummaryResponseDTO findSalesSummaryById(Long id);

    String updateSaleById(Long id);

    SalesSummaryResponseDTO updateSaleSummaryById(Long id);

    SalesSummaryResponseDTO updateSaleSummaryById(Long id, UpdateDebtorsSales request);


}
