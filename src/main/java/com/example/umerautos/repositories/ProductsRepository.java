package com.example.umerautos.repositories;

import com.example.umerautos.dto.ProductsResponseDTO;
import com.example.umerautos.entities.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductsRepository extends JpaRepository<Products, UUID> {

    @EntityGraph(attributePaths = {"brand", "shelfCode", "compatibleModels"})
    @Query("SELECT c FROM Products c WHERE (:name is NULL OR COALESCE(c.name) LIKE :name%)")
    public Page<Products> findByName(@Param("name") String name, Pageable pageable);
}
