package com.example.umerautos.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(indexes = {
        @Index(name = "name_Id_createdAt", columnList = "id, createdAt"),
})
public class Sales extends BaseModel{


    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Products product;

    @Column(nullable = false)
    private int quantitySold;

    @Column(nullable = false)
    private double totalAmount;


    @ManyToOne
    @JoinColumn(name = "sales_summary_id")
    private SalesSummary salesSummary;
}
