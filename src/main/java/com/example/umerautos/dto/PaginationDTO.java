package com.example.umerautos.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class PaginationDTO {
    private long totalItems;
    private int totalPages;
    private int currentPage;
    private int itemsPerPage;
    public PaginationDTO(long totalItems, int totalPages, int currentPage, int itemsPerPage) {
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.itemsPerPage = itemsPerPage;
    }
}
