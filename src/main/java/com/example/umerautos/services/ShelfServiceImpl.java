package com.example.umerautos.services;

import com.example.umerautos.entities.ShelfCode;
import com.example.umerautos.repositories.ShelfCodeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShelfServiceImpl implements ShelfService{
    private final ShelfCodeRepository shelfCodeRepository;

    public ShelfServiceImpl(ShelfCodeRepository shelfCodeRepository) {
        this.shelfCodeRepository = shelfCodeRepository;
    }

    @Override
    public ShelfCode createOne(ShelfCode shelfCode) {
        return shelfCodeRepository.save(shelfCode);
    }

    @Override
    public List<ShelfCode> findAll() {
        return shelfCodeRepository.findAll();
    }
}
