package com.example.umerautos.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(indexes = {
        @Index(name = "nameAndIdIndexing", columnList = "name, id"),

})
public class Products extends BaseModel {

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brandId")
    private Brands brand;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_compatible_models",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "model_id")
    )
    @Fetch(value = FetchMode.SUBSELECT)
    private Set<CompatibleModels> compatibleModels; //associate with compatible model



    @Column(nullable = false, unique = true)
    private String sku;

    private String description;

    @Column(nullable = false)
    private int quantityInStock;

    @Column(nullable = false)
    private double purchasePrice;

    private double sellingPrice;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shelfCodeId", nullable = false)
    private ShelfCode shelfCode;


}
