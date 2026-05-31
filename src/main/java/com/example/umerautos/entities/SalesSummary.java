package com.example.umerautos.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalesSummary extends BaseModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(nullable = false)
    private int quantitySold;

    @Column(nullable = false)
    private double totalAmount;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    @OneToMany(mappedBy = "salesSummary", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @Builder.Default
    private List<Sales> saleItems = new ArrayList<>();

}
