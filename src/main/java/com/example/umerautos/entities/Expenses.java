package com.example.umerautos.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(indexes = {
        @Index(name = "IdIndexing", columnList = "id"),

})
public class Expenses extends BaseModel{
    @Column(nullable = false)
    private String description;


    @Column(nullable = false)
    private double amount;

}
