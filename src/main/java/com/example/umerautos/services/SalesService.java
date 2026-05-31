package com.example.umerautos.services;


import com.example.umerautos.dto.PaginatedResponseDTO;
import com.example.umerautos.dto.SaleUpdateRequestDTO;
import com.example.umerautos.dto.SalesResponseDTO;
import com.example.umerautos.dto.SalesUpdateResponseDTO;

import java.util.List;

public interface SalesService {

    List<SalesResponseDTO> findTodaySales(int page, int limit);
    PaginatedResponseDTO<SalesResponseDTO> findAll(int page, int limit);
    SalesUpdateResponseDTO findSaleById(Long id);


    int getTodayTotalSalesAmount();

    int getMonthlyRevenue();

    SalesUpdateResponseDTO updateSale(SaleUpdateRequestDTO requestDTO, Long id);

    void deleteOne(Long id);
}
