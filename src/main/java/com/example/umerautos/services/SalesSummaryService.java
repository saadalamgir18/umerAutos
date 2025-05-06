package com.example.umerautos.services;

import com.example.umerautos.dto.SalesSummaryRequestDTO;
import com.example.umerautos.dto.SalesSummaryResponseDTO;

import java.util.List;

public interface SalesSummaryService {

    public SalesSummaryResponseDTO saveOne(SalesSummaryRequestDTO salesSummaryRequestDTO);
    public List<SalesSummaryResponseDTO> findAll();

}
