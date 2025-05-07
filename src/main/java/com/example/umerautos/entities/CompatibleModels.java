package com.example.umerautos.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompatibleModels extends BaseModel {

    private String name;

    @ManyToMany(mappedBy = "compatibleModels")
    @Fetch(FetchMode.SUBSELECT)
    private Set<Products> products;
}
