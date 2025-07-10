package com.example.umerautos.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(indexes = {
        @Index(name = "idx_shelf_code_name_id", columnList = "name, id"),

})
public class ShelfCode extends BaseModel {

    @Column(nullable = false, unique = true)
    private String name;
}
