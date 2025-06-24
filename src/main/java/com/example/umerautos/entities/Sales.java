package com.example.umerautos.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(indexes = {
        @Index(name = "name_Id_createdAt", columnList = "id, createdAt"),
})
public class Sales extends BaseModel {


    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Products product;

    @Column(nullable = false)
    private int quantitySold;

    @Column(nullable = false)
    private int totalAmount;

    @Enumerated
    @Column(
            nullable = false,
            columnDefinition = "ENUM('PAID', 'UNPAID', 'PARTIAL') DEFAULT 'PAID'"
    )
    @Builder.Default
    private PaymentStatus paymentStatus = PaymentStatus.PAID;


    @ManyToOne
    @JoinColumn(name = "sales_summary_id")
    private SalesSummary salesSummary;
}
