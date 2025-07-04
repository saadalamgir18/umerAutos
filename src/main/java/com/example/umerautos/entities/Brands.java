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
        @Index(name = "name_Id", columnList = "name, id"),

})
public class Brands extends BaseModel {

    @Column(nullable = false, unique = true)
    private String name;

    @Override
    public String toString() {
        return "Brands{" +
                "name='" + name + '\'' +
                ", Id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
