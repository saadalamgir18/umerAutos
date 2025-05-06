package com.example.umerautos.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalesSummary extends BaseModel{


    private String customerName;


    private int quantitySold;


    private double totalAmount;

    @Enumerated(value = EnumType.STRING)
    private  PaymentStatus paymentStatus;



    @OneToMany(mappedBy = "salesSummary", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Sales> saleItems = new ArrayList<>();

}
