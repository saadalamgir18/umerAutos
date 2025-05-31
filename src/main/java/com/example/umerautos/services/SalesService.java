package com.example.umerautos.services;


import com.example.umerautos.dto.PaginatedResponseDTO;
import com.example.umerautos.dto.SaleUpdateRequestDTO;
import com.example.umerautos.dto.SalesResponseDTO;
import com.example.umerautos.dto.SalesUpdateResponseDTO;

import java.util.List;
import java.util.UUID;

public interface SalesService {

    List<SalesResponseDTO> findTodaySales(int page, int limit);
    PaginatedResponseDTO<SalesResponseDTO> findAll(int page, int limit);
    SalesUpdateResponseDTO findSaleById(UUID id);


    int getTodayTotalSalesAmount();

    int getMonthlyRevenue();

    SalesUpdateResponseDTO updateSale(SaleUpdateRequestDTO requestDTO, UUID id);

    void deleteOne(UUID id);
}
