package com.example.umerautos.controllers;


import com.example.umerautos.entities.Brands;
import com.example.umerautos.services.BrandsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BrandsController {
    private BrandsService brandsService;

    public BrandsController(BrandsService brandsService) {
        this.brandsService = brandsService;
    }

    @GetMapping("/brands")
    public List<Brands> findAll(){

        return brandsService.findAll();

    }

    @PostMapping("/brands")
    public Brands saveOne(@RequestBody Brands brands){
        return brandsService.createOne(brands);
    }
}
