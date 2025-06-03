package com.example.umerautos.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder

public record PaginationDTO(
        long totalItems, int totalPages, int currentPage, int itemsPerPage
) {

}
