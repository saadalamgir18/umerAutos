package com.example.umerautos.entities;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Khata extends BaseModel {
    private String customerName;
    private String phoneNumber;
    private  double outstandingAmount;

}
