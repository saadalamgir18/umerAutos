package com.example.umerautos.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
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
    protected UUID id;

    @Column(nullable = false)
    @CreationTimestamp
    protected Date createdAt;


    @Column(nullable = false)
    @UpdateTimestamp
    protected Date updatedAt;
}
