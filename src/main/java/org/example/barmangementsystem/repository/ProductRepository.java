package org.example.barmangementsystem.repository;
import org.example.barmangementsystem.entity.Category;
import org.example.barmangementsystem.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByName(String name);

    boolean existsByCategory(Category category);
    boolean existsByNameAndCategory(String name, Category category);

    List<Product> findByStockQuantityLessThanEqual(int i);
}