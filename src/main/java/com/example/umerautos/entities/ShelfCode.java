package com.example.umerautos.entities;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(indexes = {
        @Index(name = "name_Id", columnList = "name, id"),

})
public class ShelfCode extends BaseModel {

    @Column(nullable = false, unique = true)
    private String name;
}
