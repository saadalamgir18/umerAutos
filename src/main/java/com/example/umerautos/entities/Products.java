package com.example.umerautos.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;
import java.util.Set;
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



    @ManyToMany
    @JoinTable(
            name = "product_compatible_models",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "model_id")
    )
    private Set<CompatibleModels> compatibleModels; //associate with compatible model

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
