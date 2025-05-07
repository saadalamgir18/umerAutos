package com.example.umerautos.services;


import com.example.umerautos.dto.SalesResponseDTO;

import java.util.List;

public interface SalesService {

    List<SalesResponseDTO> findAll();
    Double getTodayTotalSalesAmount();
}
