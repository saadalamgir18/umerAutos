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
public class Khata extends BaseModel {

    @Column(nullable = false, unique = true)
    private String customerName;
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private  double outstandingAmount;

}
