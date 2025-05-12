package com.example.umerautos.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Expenses extends BaseModel{
    @Column(nullable = false)
    private String description;


    @Column(nullable = false)
    private double amount;

}
