package com.example.umerautos.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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


    @ManyToOne
    @JoinColumn(name = "sales_summary_id")
    private SalesSummary salesSummary;
}
