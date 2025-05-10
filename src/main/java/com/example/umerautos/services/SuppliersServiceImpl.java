package com.example.umerautos.services;

import com.example.umerautos.dto.SuppliersRequestDTO;
import com.example.umerautos.dto.SuppliersResponseDTO;
import com.example.umerautos.entities.Suppliers;
import com.example.umerautos.globalException.ResourceNotFoundException;
import com.example.umerautos.repositories.SuppliersRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SuppliersServiceImpl implements SuppliersService{
    @Autowired private SuppliersRepository suppliersRepository;
    @Override
    public List<SuppliersResponseDTO> findAll() {
        List<Suppliers> suppliers = suppliersRepository.findAll();

        return suppliers.stream().map(SuppliersResponseDTO::mapToDTO).toList();
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
