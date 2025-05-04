package com.example.umerautos.entities;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID Id;

    @Column(nullable = false)
    @CreationTimestamp
    protected Date createdAt;


    @Column(nullable = false)
    @UpdateTimestamp
    protected Date updatedAt;
}
