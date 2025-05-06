package com.example.umerautos.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sales extends BaseModel{


    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Products product;

    private int quantitySold;

    private double totalAmount;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sales_summary_id")
    private SalesSummary salesSummary;
}
