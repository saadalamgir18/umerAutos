package com.example.umerautos.controllers;

import com.example.umerautos.entities.ShelfCode;
import com.example.umerautos.services.ShelfService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ShelfController {

    private final ShelfService shelfService;

    public ShelfController(ShelfService shelfService) {
        this.shelfService = shelfService;
    }

    @PostMapping("/shelf")
    public ShelfCode saveOne(@RequestBody ShelfCode shelfCode){
        return shelfService.createOne(shelfCode);
    }

    @GetMapping("/shelf")
    public List<ShelfCode> findAll(){
        return shelfService.findAll();

    }

}
