package com.example.umerautos.entities;

import jakarta.persistence.*;
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
@Table(indexes = {
        @Index(name = "name_Id", columnList = "name, id"),

})
public class CompatibleModels extends BaseModel {

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "compatibleModels")
    @Fetch(FetchMode.SUBSELECT)
    private Set<Products> products;
}
