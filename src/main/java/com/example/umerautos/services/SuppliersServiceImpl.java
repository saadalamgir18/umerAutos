package com.example.umerautos.services;

import com.example.umerautos.dto.PaginatedResponseDTO;
import com.example.umerautos.dto.PaginationDTO;
import com.example.umerautos.dto.SuppliersRequestDTO;
import com.example.umerautos.dto.SuppliersResponseDTO;
import com.example.umerautos.entities.Suppliers;
import com.example.umerautos.globalException.ResourceNotFoundException;
import com.example.umerautos.repositories.SuppliersRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SuppliersServiceImpl implements SuppliersService{

    @Autowired private SuppliersRepository suppliersRepository;

    @Override
    public PaginatedResponseDTO<SuppliersResponseDTO> findAll(int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);

        Page<Suppliers> suppliers = suppliersRepository.findAll(pageable);
        PaginationDTO pagination = new PaginationDTO(
                suppliers.getTotalElements(),
                suppliers.getTotalPages(),
                page,
                limit
        );

        List<SuppliersResponseDTO> suppliersResponseDTOS = suppliers.stream().map(SuppliersResponseDTO::mapToDTO).toList();

        return new PaginatedResponseDTO<>(suppliersResponseDTOS, pagination);
    }

    @Override
    public SuppliersResponseDTO saveOne(SuppliersRequestDTO supplier) {
        Suppliers suppliers = Suppliers.builder()
                .company(supplier.getCompany())
                .contactPerson(supplier.getContactPerson())
                .email(supplier.getEmail())
                .phoneNumber(supplier.getPhoneNumber())
                .build();
        ;
        return SuppliersResponseDTO.mapToDTO(suppliersRepository.save(suppliers)) ;
    }

    @SneakyThrows
    @Override
    public void deleteOne(UUID id) {
        Optional<Suppliers> supplier = suppliersRepository.findById(id);
        if (supplier.isPresent()){
            suppliersRepository.deleteById(id);
        }else {
            throw new ResourceNotFoundException();
        }

    }
}
