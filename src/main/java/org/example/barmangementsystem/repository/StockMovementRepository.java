package org.example.barmangementsystem.repository;


import org.example.barmangementsystem.entity.Product;
import org.example.barmangementsystem.entity.StockMovement;
import org.example.barmangementsystem.entity.StockMovementType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

    List<StockMovement> findAllByOrderByDateDesc();

    List<StockMovement> findByProductOrderByDateDesc(Product product);

    List<StockMovement> findByDateBetweenOrderByDateDesc(LocalDateTime start, LocalDateTime end);

    List<StockMovement> findByProductAndDateBetweenOrderByDateDesc(Product product, LocalDateTime start, LocalDateTime end);

    StockMovement[] findByReference(String s);
    List<StockMovement> findByTypeOrderByDateDesc(StockMovementType type);
}

