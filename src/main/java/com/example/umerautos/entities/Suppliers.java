package com.example.umerautos.entities;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Suppliers extends BaseModel{

    private String Company;

    private String contactPerson;

    private String email;

    private String phoneNumber;

}
