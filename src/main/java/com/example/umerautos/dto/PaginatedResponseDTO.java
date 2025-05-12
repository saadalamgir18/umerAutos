package com.example.umerautos.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaginatedResponseDTO <T>{
    private List<T> data;
    private PaginationDTO pagination;

    public PaginatedResponseDTO(List<T> data, PaginationDTO pagination) {
        this.data = data;
        this.pagination = pagination;
    }
}
