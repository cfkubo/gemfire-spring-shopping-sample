package com.example.repository;

import com.example.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "SELECT prod_id FROM products ORDER BY prod_id ASC LIMIT :count", nativeQuery = true)
    List<Integer> findFirstNIds(@Param("count") int count);
}