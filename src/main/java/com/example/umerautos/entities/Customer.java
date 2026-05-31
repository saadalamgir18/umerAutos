package com.example.umerautos.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Customer")
public class Customer extends BaseModel {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String idCardNo;
}
