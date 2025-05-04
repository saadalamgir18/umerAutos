package com.example.umerautos.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Products extends BaseModel {

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "brandId")
    private Brands brand;



    @ManyToOne
    @JoinColumn(name = "modelId")
    private CompatibleModels compatibleModels; //associate with compatible model

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category; // associate with category


    @Column(nullable = false, unique = true)
    private String sku;

    private String description;

    private int quantityInStock;

    private double purchasePrice;

    private double sellingPrice;

    @ManyToOne
    @JoinColumn(name = "supplierId")
    private Suppliers supplierId; //associate with supplier

    @ManyToOne
    @JoinColumn(name = "shelfCodeId")
    private ShelfCode shelfCode;



}
