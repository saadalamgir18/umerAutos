package com.example.umerautos.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShelfCode extends BaseModel {

    @Column(nullable = false, unique = true)
    private String name;
}
