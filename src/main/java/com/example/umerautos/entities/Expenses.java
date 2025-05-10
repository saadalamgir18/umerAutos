package com.example.umerautos.entities;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Expenses extends BaseModel{
    private String description;
    private double amount;

}
