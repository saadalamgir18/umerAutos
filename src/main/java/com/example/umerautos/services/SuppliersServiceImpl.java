package com.example.umerautos.services;

import com.example.umerautos.dto.PaginatedResponseDTO;
import com.example.umerautos.dto.PaginationDTO;
import com.example.umerautos.dto.SuppliersRequestDTO;
import com.example.umerautos.dto.SuppliersResponseDTO;
import com.example.umerautos.entities.Suppliers;
import com.example.umerautos.globalException.ResourceNotFoundException;
import com.example.umerautos.repositories.SuppliersRepository;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SuppliersServiceImpl implements SuppliersService {

    @Autowired
    private SuppliersRepository suppliersRepository;
    private static final Logger logger = LoggerFactory.getLogger(SuppliersServiceImpl.class);

    @Override
    public PaginatedResponseDTO<SuppliersResponseDTO> findAll(int page, int limit) {
        logger.info("Fetching all suppliers. Page: {}, Limit: {}", page, limit);
        Pageable pageable = PageRequest.of(page - 1, limit);

        Page<Suppliers> suppliers = suppliersRepository.findAll(pageable);
        logger.info("Found {} suppliers", suppliers.getTotalElements());
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
        logger.info("Creating new supplier: {}", supplier.company());
        Suppliers suppliers = Suppliers.builder()
                .company(supplier.company())
                .contactPerson(supplier.contactPerson())
                .email(supplier.email())
                .phoneNumber(supplier.phoneNumber())
                .build();
        ;
        Suppliers savedSupplier = suppliersRepository.save(suppliers);
        logger.info("Supplier created with id: {}", savedSupplier.getId());
        return SuppliersResponseDTO.mapToDTO(savedSupplier);
    }

    @SneakyThrows
    @Override
    public void deleteOne(Long id) {
        logger.info("Deleting supplier with id: {}", id);
        Optional<Suppliers> supplier = suppliersRepository.findById(id);
        if (supplier.isPresent()) {
            suppliersRepository.deleteById(id);
            logger.info("Supplier deleted successfully");
        } else {
            logger.error("Supplier not found for deletion with id: {}", id);
            throw new ResourceNotFoundException("supplier does not exist with id: " + id);
        }

    }
}
