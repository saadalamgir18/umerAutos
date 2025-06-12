package com.example.umerautos.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SalesPerson extends BaseModel {
    @Column(nullable = false)
    private String userName;

    @Column(nullable = false, unique = true)
    private String email;


    @Column(nullable = false)
    private String password;

    @Builder.Default
    private String role = "USER";

}
